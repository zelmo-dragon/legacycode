package com.github.legacycode.jakarta.persistence;

import java.util.Optional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import com.github.legacycode.core.Identifiable;
import com.github.legacycode.core.Repository;

public abstract class AbstractDAO<E extends Identifiable<K>, K> implements Repository<E, K> {

    protected final transient EntityManager em;

    protected AbstractDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public void add(E entity) {
    }

    @Override
    public void remove(K key) {
    }

    @Override
    public boolean contains(K key) {
        return false;
    }

    @Override
    public Optional<E> find(K key) {
        return Optional.empty();
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
