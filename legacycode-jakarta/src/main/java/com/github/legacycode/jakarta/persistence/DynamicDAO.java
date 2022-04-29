package com.github.legacycode.jakarta.persistence;

import java.io.Serializable;
import java.util.Optional;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Dependent
public class DynamicDAO implements Serializable {

    private final transient EntityManager em;

    @Inject
    public DynamicDAO(EntityManager em) {
        this.em = em;
    }

    public <E extends Entity<K>, K> void add(Class<E> entityClass, E entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <E extends Entity<K>, K> void remove(Class<E> entityClass, K key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <E extends Entity<K>, K> boolean contains(Class<E> entityClass, K key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <E extends Entity<K>, K> Optional<E> find(Class<E> entityClass, K key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    <X> Optional<X> findReference(Class<X> entityClass, Object id) {
        return Optional.ofNullable(this.em.find(entityClass, id));
    }

}
