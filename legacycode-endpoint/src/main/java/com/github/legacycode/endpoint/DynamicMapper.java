package com.github.legacycode.endpoint;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DynamicMapper<E, D> {

    E toEntity(D data);

    default List<E> toEntity(List<D> data) {
        return Optional
                .ofNullable(data)
                .map(Collection::stream)
                .map(d -> d.map(this::toEntity).toList())
                .orElseGet(List::of);

    }

    D toData(E entity);

    default List<D> toData(List<E> entities) {
        return Optional
                .ofNullable(entities)
                .map(Collection::stream)
                .map(d -> d.map(this::toData).toList())
                .orElseGet(List::of);

    }

    void updateFromData(D source, E target);

}
