package com.github.legacycode.endpoint;

import java.util.Set;

public record DynamicEntry<E, D, M extends DynamicMapper<E, D>, S extends DynamicService<E, D>>(
        String name,
        Set<Action> actions,
        Class<E> entityClass,
        Class<D> dataClass,
        Class<M> mapper,
        Class<S> service) {


}
