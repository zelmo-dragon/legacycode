package com.github.legacycode.jakarta.persistence;

import java.lang.reflect.ParameterizedType;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
        var puu = this.em.getEntityManagerFactory().getPersistenceUnitUtil();
        var id = (K) puu.getIdentifier(entity);
        this.remove(id);
    }

    @Override
    public void remove(K key) {
        var entityClass = getEntityClass();
        var attachedEntity = this.em.getReference(entityClass, key);
        this.em.remove(attachedEntity);
    }


    @Override
    public boolean contains(E entity) {
        return this.em.contains(entity);
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

}
