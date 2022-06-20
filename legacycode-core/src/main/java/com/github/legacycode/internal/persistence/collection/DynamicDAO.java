package com.github.legacycode.internal.persistence.collection;

import com.github.legacycode.core.repository.DynamicRepository;
import com.github.legacycode.core.repository.Identifiable;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Collection
public final class DynamicDAO implements DynamicRepository {

    public DynamicDAO() {
    }

    @Override
    public <K, E extends Identifiable<K>> void add(final Class<E> entityClass, final E entity) {
        DB.getInstance().put(entityClass, Set.of(entity));
    }

    @Override
    public <K, E extends Identifiable<K>> void remove(final Class<E> entityClass, final K key) {
        DB.getInstance().removeIf(entityClass, e -> Objects.equals(e.getId(), key));
    }

    @Override
    public <K, E extends Identifiable<K>> boolean contains(final Class<E> entityClass, final K key) {
        return DB.getInstance().contains(entityClass, e -> Objects.equals(e.getId(), key));
    }

    @Override
    public <K, E extends Identifiable<K>> Optional<E> find(final Class<E> entityClass, final K key) {
        return DB.getInstance().get(entityClass, e -> Objects.equals(e.getId(), key));
    }
}
