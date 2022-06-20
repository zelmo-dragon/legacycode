package com.github.legacycode.infrastructure.persistence;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.metamodel.SingularAttribute;

public abstract class AbstractMapDAO<K, E> implements Map<K, E> {

    protected final EntityManager em;

    protected AbstractMapDAO(final EntityManager em) {
        this.em = em;
    }

    public Optional<E> find(K id) {
        return Optional
                .ofNullable(id)
                .map(this::get);
    }

    public void put(E entity) {
        var id = this.getPrimaryKey(entity);
        this.put(id, entity);
    }

    protected Collection<E> filter(final CriteriaPredicate<E, E> criteria) {
        return this.createQuery(criteria).getResultList();
    }

    @Override
    public int size() {
        CriteriaPredicate<E, Long> criteria = (b, r, q) -> {
            q.select(b.count(r));
            return b.and();
        };

        return this.createQuery(Long.class, criteria)
                .getSingleResult()
                .intValue();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(final Object key) {

        CriteriaPredicate<E, Long> criteria = (b, r, q) -> {
            q.select(b.count(r));
            var attribut = getPrimaryKeyAttribut();
            return b.equal(r.get(attribut), key);
        };

        return this.createQuery(Long.class, criteria)
                .getSingleResult() >= 1L;
    }

    @Override
    public boolean containsValue(final Object value) {
        CriteriaPredicate<E, Long> criteria = (b, r, q) -> {
            q.select(b.count(r));
            var id = getPrimaryKey(value);
            var attribut = getPrimaryKeyAttribut();
            return b.equal(r.get(attribut), id);
        };

        return this.createQuery(Long.class, criteria)
                .getSingleResult() >= 1L;
    }

    @Override
    public E get(final Object key) {
        return this.em.find(this.getEntityClass(), key);
    }

    @Override
    public E put(final K key, final E value) {
        E attachedEntity;
        if (this.em.contains(value)) {
            attachedEntity = this.em.merge(value);
        } else {
            this.em.persist(value);
            attachedEntity = value;
        }
        return attachedEntity;
    }

    @Override
    public E remove(final Object key) {
        var entityClass = this.getEntityClass();
        var attachedEntity = this.em.getReference(entityClass, key);
        this.em.remove(attachedEntity);
        return attachedEntity;
    }

    @Override
    public void putAll(final Map<? extends K, ? extends E> map) {
        map.forEach(this::put);
    }

    @Override
    public void clear() {
        var entityClass = this.getEntityClass();
        var builder = this.em.getCriteriaBuilder();
        var query = builder.createCriteriaDelete(entityClass);
        var root = query.from(entityClass);
        this.em.createQuery(query).executeUpdate();
    }

    @Override
    public Set<K> keySet() {
        CriteriaPredicate<E, K> criteria = (b, r, q) -> {
            var attribut = getPrimaryKeyAttribut();
            q.select(r.get(attribut));
            return b.and();
        };

        return this.createQuery(this.getKeyClass(), criteria)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<E> values() {
        CriteriaPredicate<E, E> criteria = (b, r, q) -> {
            q.select(r);
            return b.and();
        };

        return this.createQuery(criteria)
                .getResultList();
    }

    @Override
    public Set<Entry<K, E>> entrySet() {
        return this.values()
                .stream()
                .map(this::map)
                .collect(Collectors.toSet());
    }

    private Map.Entry<K, E> map(E entity) {
        var id = this.getPrimaryKey(entity);
        return new EntityEntry<>(id, entity);
    }

    protected Class<K> getKeyClass() {
        var paramType = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<K>) paramType.getActualTypeArguments()[0];
    }

    protected Class<E> getEntityClass() {
        var paramType = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<E>) paramType.getActualTypeArguments()[1];
    }

    protected SingularAttribute<E, K> getPrimaryKeyAttribut() {
        var entityClass = this.getEntityClass();
        return this.em.getMetamodel()
                .entity(entityClass)
                .getDeclaredSingularAttributes()
                .stream()
                .filter(SingularAttribute::isId)
                .map(e -> (SingularAttribute<E, K>) e)
                .findFirst()
                .orElseThrow(() -> new PersistenceException("No primary key attribut found in class: " + entityClass));
    }

    protected K getPrimaryKey(final Object entity) {
        var puu = this.em.getEntityManagerFactory().getPersistenceUnitUtil();
        return (K) puu.getIdentifier(entity);
    }

    protected <R> TypedQuery<R> createQuery(
            final Class<R> targetClass,
            final CriteriaPredicate<E, R> criteria) {

        var entityClass = this.getEntityClass();
        var builder = this.em.getCriteriaBuilder();
        var query = builder.createQuery(targetClass);
        var root = query.from(entityClass);
        var predicate = criteria.toPredicate(builder, root, query);
        query.where(predicate);
        return em.createQuery(query);
    }

    protected TypedQuery<E> createQuery(final CriteriaPredicate<E, E> criteria) {

        var entityClass = this.getEntityClass();
        return createQuery(entityClass, criteria);
    }
}
