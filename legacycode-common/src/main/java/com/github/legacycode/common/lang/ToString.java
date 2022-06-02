package com.github.legacycode.common.lang;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ToString<E> {

    private final Map<String, Function<E, Object>> getters;

    private ToString() {
        this.getters = new HashMap<>();
    }

    public static <E> ToString<E> with(final String fieldName, final Function<E, Object> getter) {
        var toString = new ToString<E>();
        toString.getters.put(fieldName, getter);
        return toString;
    }

    public ToString<E> thenWith(final String fieldName, final Function<E, Object> getter) {
        this.getters.put(fieldName, getter);
        return this;
    }

    public String apply(final E element) {
        Objects.requireNonNull(element);
        var className = element.getClass().getSimpleName();
        var fields = this.getters
                .entrySet()
                .stream()
                .map(g -> ToString.format(g, element))
                .collect(Collectors.joining(","));

        return new StringBuilder()
                .append(className)
                .append('[')
                .append(fields)
                .append(']')
                .toString();
    }

    private static <E> String format(
            final Map.Entry<String, Function<E, Object>> entry,
            final E element) {

        var key = entry.getKey();
        var value = String.valueOf(entry.getValue().apply(element));
        return String.join("=", key, value);
    }

}
