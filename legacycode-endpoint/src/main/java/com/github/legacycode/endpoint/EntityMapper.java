package com.github.legacycode.endpoint;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EntityMapper<E, D> {

    E toEntity(D data);

    default List<E> toEntity(List<D> data) {
        return Optional
                .ofNullable(data)
                .map(Collection::stream)
                .map(d -> d.map(this::toEntity).toList())
                .orElseGet(List::of);

    }

    D fromEntity(E entity);

    default List<D> fromEntity(List<E> entities) {
        return Optional
                .ofNullable(entities)
                .map(Collection::stream)
                .map(d -> d.map(this::fromEntity).toList())
                .orElseGet(List::of);

    }

    void updateEntity(D source, E target);

}
