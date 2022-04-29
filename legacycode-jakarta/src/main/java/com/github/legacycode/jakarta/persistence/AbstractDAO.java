package com.github.legacycode.jakarta.persistence;

import java.lang.reflect.ParameterizedType;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.SingularAttribute;

public abstract class AbstractDAO<E extends Entity<K>, K> implements DAO<E, K> {

    protected final transient EntityManager em;

    protected AbstractDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public E add(E entity) {
        E attachedEntity;
        if (this.em.contains(entity)) {
            attachedEntity = this.em.merge(entity);
        } else {
            this.em.persist(entity);
            attachedEntity = entity;
        }
        return attachedEntity;
    }

    @Override
    public void remove(final E entity) {
        var entityClass = getEntityClass();
        var puu = this.em.getEntityManagerFactory().getPersistenceUnitUtil();
        var id = puu.getIdentifier(entity);
        var attachedEntity = this.em.getReference(entityClass, id);
        this.em.remove(attachedEntity);
    }

    @Override
    public boolean contains(E entity) {
        var cb = this.em.getCriteriaBuilder();
        var cq = cb.createQuery(Long.class);
        var root = cq.from(getEntityClass());
        cq.select(cb.count(root));
        var puu = this.em.getEntityManagerFactory().getPersistenceUnitUtil();
        var id = puu.getIdentifier(entity);
        var attribut = getPrimaryKeyAttribut();
        var p0 = cb.equal(root.get(attribut), id);
        cq.where(p0);
        return this.em.createQuery(cq).getSingleResult() > 0;
    }

    @Override
    public Optional<E> find(K key) {
        var entityClass = getEntityClass();
        var attachedEntity = this.em.find(entityClass, key);
        return Optional.ofNullable(attachedEntity);
    }

    protected Class<E> getEntityClass() {
        var paramType = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<E>) paramType.getActualTypeArguments()[0];
    }

    protected <T, R> TypedQuery<R> createQuery(
            final Class<T> queryRoot,
            final Class<R> queryType,
            final PredicateBuilder<T, R> predicateBuilder) {

        var cb = this.em.getCriteriaBuilder();
        var query = cb.createQuery(queryType);
        var root = query.from(queryRoot);
        var predicate = predicateBuilder.build(cb, root, query);
        query.where(predicate);

        return this.em.createQuery(query);
    }

    protected <R> TypedQuery<R> createQuery(
            final Class<R> queryRoot,
            final PredicateBuilder<R, R> predicateBuilder) {

        return this.createQuery(queryRoot, predicateBuilder);
    }

    private SingularAttribute<E, ?> getPrimaryKeyAttribut() {
        var entityClass = getEntityClass();
        return this.em
                .getMetamodel()
                .entity(entityClass)
                .getDeclaredSingularAttributes()
                .stream()
                .filter(SingularAttribute::isId)
                .findFirst()
                .orElseThrow(() -> new PersistenceException("No primary key attribut found in class: " + entityClass));

    }

}
