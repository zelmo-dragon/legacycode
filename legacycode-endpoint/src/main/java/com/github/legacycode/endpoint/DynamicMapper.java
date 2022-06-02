package com.github.legacycode.endpoint;

public interface DynamicMapper<E, D> {

    E toEntity(D data);

    D toData(E entity);

    void updateFromData(D source, E target);

}
