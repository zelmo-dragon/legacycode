package com.github.legacycode.jakarta.persistence;

import java.io.Serializable;
import java.util.Optional;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import com.github.legacycode.core.Identifiable;
import com.github.legacycode.core.common.DynamicRepository;

@Dependent
public class DynamicDAO implements DynamicRepository, Serializable {

    private final transient EntityManager em;

    @Inject
    public DynamicDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public <E extends Identifiable<K>, K> void add(Class<E> entityClass, E entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E extends Identifiable<K>, K> void remove(Class<E> entityClass, K key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E extends Identifiable<K>, K> boolean contains(Class<E> entityClass, K key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E extends Identifiable<K>, K> Optional<E> find(Class<E> entityClass, K key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    <X> Optional<X> findReference(Class<X> entityClass, Object id) {
        return Optional.ofNullable(this.em.find(entityClass, id));
    }

}
