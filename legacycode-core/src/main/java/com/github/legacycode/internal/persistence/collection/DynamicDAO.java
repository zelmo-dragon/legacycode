package com.github.legacycode.internal.persistence.collection;

import com.github.legacycode.core.Identifiable;
import com.github.legacycode.core.common.DynamicRepository;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Collection
public final class DynamicDAO implements DynamicRepository {

    public DynamicDAO() {
    }

    @Override
    public <E extends Identifiable<K>, K> void add(
            final Class<E> entityClass,
            final E entity) {

        DB
                .getInstance()
                .put(entityClass, Set.of(entity));
    }

    @Override
    public <E extends Identifiable<K>, K> void remove(
            final Class<E> entityClass,
            final K key) {

        DB
                .getInstance()
                .removeIf(entityClass, e -> Objects.equals(e.getBusinessKey(), key));
    }

    @Override
    public <E extends Identifiable<K>, K> boolean contains(
            final Class<E> entityClass,
            final K key) {

        return DB
                .getInstance()
                .contains(entityClass, e -> Objects.equals(e.getBusinessKey(), key));
    }

    @Override
    public <E extends Identifiable<K>, K> Optional<E> find(Class<E> entityClass, K key) {

        return DB
                .getInstance()
                .get(entityClass, e -> Objects.equals(e.getBusinessKey(), key));
    }

}
