package com.github.legacycode.core.repository;

import java.util.Map;
import java.util.Optional;

public interface Repository<K, E extends Identifiable<K>> extends Map<K, E> {

    default Optional<E> find(K id) {
        return Optional
                .ofNullable(id)
                .map(this::get);
    }

    default void put(E entity) {
        var id = entity.getId();
        this.put(id, entity);
    }
}
