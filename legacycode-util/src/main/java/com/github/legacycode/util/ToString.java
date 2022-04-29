package com.github.legacycode.util;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ToString<E> {

    private final Set<Function<E, Object>> getters;

    private ToString() {
        this.getters = new HashSet<>();
    }

    public static <E> ToString<E> with(final Function<E, Object> getter) {
        var string = new ToString<E>();
        string.getters.add(getter);
        return string;
    }

    public ToString<E> thenWith(final Function<E, Object> getter) {
        this.getters.add(getter);
        return this;
    }

    public String apply(final E entity) {
        var className = entity.getClass().getSimpleName();
        var fields = this.getters
                .stream()
                .map(g -> g.apply(entity))
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        return new StringBuilder()
                .append(className)
                .append('[')
                .append(fields)
                .append(']')
                .toString();
    }

}
