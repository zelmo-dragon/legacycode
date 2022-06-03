package com.github.legacycode.endpoint;

import java.util.Set;

public record EntityEntry<E, D, M extends EntityMapper<E, D>, S extends EndpointService>(
        String name,
        Set<Action> actions,
        Class<E> entityClass,
        Class<D> dataClass,
        Class<M> mapperClass,
        Class<S> serviceClass) {

}
