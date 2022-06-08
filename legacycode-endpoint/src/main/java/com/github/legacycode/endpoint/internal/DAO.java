package com.github.legacycode.endpoint.internal;


import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.SingularAttribute;


@ApplicationScoped
public class DAO implements Serializable {

    private transient final EntityManager em;

    @Inject
    public DAO(final EntityManager em) {
        this.em = em;
    }

    <E> void remove(final Class<E> entityClass, final Object id) {
        var attachedEntity = this.em.getReference(entityClass, id);
        this.em.remove(attachedEntity);
    }

    <E> E save(final E entity) {
        E managedEntity;
        if (this.em.contains(entity)) {
            managedEntity = this.em.merge(entity);
        } else {
            this.em.persist(entity);
            managedEntity = entity;
        }
        return managedEntity;
    }

    <E> Optional<E> find(final Class<E> entityClass, final Object id) {
        var entity = this.em.find(entityClass, id);
        return Optional.ofNullable(entity);
    }

    <E> List<E> find(
            final Class<E> entityClass,
            final Set<FilterQuery> queries) {

        var distinct = Queries.isDistinct(queries);

        InternalCriteriaPredicate<E, E> predicate = (b, r, q) -> {
            q.distinct(distinct);
            q.select(r);
            var orders = buildOrder(queries, b, r);
            q.orderBy(orders);
            return buildPredicate(em, entityClass, b, r, queries);
        };

        var pageSize = Queries.getPageSize(queries);
        var pageNumber = Queries.getPageNumber(queries);
        var startPosition = Math.max(0, (pageNumber - 1) * pageSize);

        return createQuery(this.em, entityClass, predicate)
                .setFirstResult(startPosition)
                .setMaxResults(pageSize)
                .getResultList();
    }

    <E> long size(
            final Class<E> entityClass,
            final Set<FilterQuery> queries) {

        InternalCriteriaPredicate<E, Long> predicate = (b, r, q) -> {
            q.select(b.count(r));
            return buildPredicate(em, entityClass, b, r, queries);
        };

        return createQuery(this.em, entityClass, Long.class, predicate)
                .getSingleResult();
    }

    <E> boolean contains(final E entity) {

        var entityClass = (Class<E>) entity.getClass();
        InternalCriteriaPredicate<E, Long> predicate = (b, r, q) -> {
            q.select(b.count(r));
            var id = getPrimaryKey(entity);
            var attribut = getPrimaryKeyAttribut(this.em, entityClass);
            return b.equal(r.get(attribut), id);
        };

        return createQuery(this.em, entityClass, Long.class, predicate)
                .getSingleResult() >= 1L;

    }

    <E, K> K getPrimaryKey(final E entity) {
        var puu = this.em.getEntityManagerFactory().getPersistenceUnitUtil();
        return (K) puu.getIdentifier(entity);
    }

    private static <E> SingularAttribute<E, ?> getPrimaryKeyAttribut(
            final EntityManager em,
            final Class<E> entityClass) {

        return em.getMetamodel()
                .entity(entityClass)
                .getDeclaredSingularAttributes()
                .stream()
                .filter(SingularAttribute::isId)
                .findFirst()
                .orElseThrow(() -> new PersistenceException("No primary key attribut found in class: " + entityClass));
    }

    private static <E, R> TypedQuery<R> createQuery(
            final EntityManager em,
            final Class<E> entityClass,
            final Class<R> targetClass,
            final InternalCriteriaPredicate<E, R> criteria) {

        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(targetClass);
        var root = query.from(entityClass);
        var predicate = criteria.toPredicate(builder, root, query);
        query.where(predicate);
        return em.createQuery(query);
    }

    private static <E> TypedQuery<E> createQuery(
            final EntityManager em,
            final Class<E> entityClass,
            final InternalCriteriaPredicate<E, E> criteria) {

        return createQuery(em, entityClass, entityClass, criteria);
    }


    private static <E> List<Order> buildOrder(
            final Set<FilterQuery> queries,
            final CriteriaBuilder builder,
            final Root<E> root) {

        return queries
                .stream()
                .filter(FilterQuery::isSortQuery)
                .map(FilterQuery::getSortedValues)
                .map(Map::entrySet)
                .map(Set::stream)
                .map(e -> (Map.Entry<String, Boolean>) e)
                .map(e -> buildOrder(e, builder, root))
                .collect(Collectors.toList());
    }

    private static <E> Order buildOrder(
            final Map.Entry<String, Boolean> attribute,
            final CriteriaBuilder builder,
            final Root<E> root) {

        Order order;
        if (Objects.equals(attribute.getValue(), Boolean.TRUE)) {
            order = builder.asc(root.get(attribute.getKey()));
        } else {
            order = builder.desc(root.get(attribute.getKey()));
        }
        return order;
    }

    private static <E> Predicate buildPredicate(
            final EntityManager em,
            final Class<E> entityClass,
            final CriteriaBuilder builder,
            final Root<E> root,
            final Set<FilterQuery> queries) {


        return builder.and(queries
                .stream()
                .filter(q -> isSafeSearchQuery(em, entityClass, q))
                .map(q -> buildPredicate(builder, root, q))
                .filter(Optional::isPresent)
                .flatMap(Optional::stream)
                .toArray(Predicate[]::new)
        );
    }

    private static <X> Optional<Predicate> buildPredicate(
            final CriteriaBuilder builder,
            final Root<X> root,
            final FilterQuery query) {

        Predicate predicate;
        if (!query.isBasicQuery()) {
            predicate = switch (query.getOperator()) {
                case EQUAL -> FilterPredicate.equal(builder, root, query);
                case NOT_EQUAL -> FilterPredicate.notEqual(builder, root, query);
                case LIKE -> FilterPredicate.like(builder, root, query);
                case NOT_LIKE -> FilterPredicate.notLike(builder, root, query);
                case GREATER_THAN -> FilterPredicate.greaterThan(builder, root, query);
                case GREATER_THAN_OR_EQUAL -> FilterPredicate.greaterThanOrEqual(builder, root, query);
                case LESS_THAN -> FilterPredicate.lessThan(builder, root, query);
                case LESS_THAN_OR_EQUAL -> FilterPredicate.lessThanOrEqual(builder, root, query);
                case IN -> FilterPredicate.in(builder, root, query);
                case NOT_IN -> FilterPredicate.notIn(builder, root, query);
                case BETWEEN -> FilterPredicate.between(builder, root, query);
                case NOT_BETWEEN -> FilterPredicate.notBetween(builder, root, query);
                default -> null;
            };
        } else if (query.isKeywordQuery()) {
            predicate = FilterPredicate.keyword(builder, root, query);
        } else {
            predicate = null;
        }
        return Optional.ofNullable(predicate);
    }

    private static <E> boolean isSafeSearchQuery(
            final EntityManager em,
            final Class<E> entityClass,
            final FilterQuery query) {

        return em
                .getMetamodel()
                .entity(entityClass)
                .getAttributes()
                .stream()
                .filter(a -> Objects.equals(a.getPersistentAttributeType(), Attribute.PersistentAttributeType.BASIC))
                .anyMatch(a -> Objects.equals(a.getName(), query.getName()));
    }

    @FunctionalInterface
    private interface InternalCriteriaPredicate<E, R> {

        Predicate toPredicate(CriteriaBuilder builder, Root<E> root, CriteriaQuery<R> query);
    }

}
