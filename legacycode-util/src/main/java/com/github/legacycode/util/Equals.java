package com.github.legacycode.util;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public final class Equals<E> {

    private final Set<Function<E, Object>> getters;

    private Equals() {
        this.getters = new HashSet<>();
    }

    public static <E> Equals<E> with(final Function<E, Object> getter) {
        var equality = new Equals<E>();
        equality.getters.add(getter);
        return equality;
    }

    public Equals<E> thenWith(final Function<E, Object> getter) {
        this.getters.add(getter);
        return this;
    }

    public boolean apply(final E entity, final Object any) {
        Objects.requireNonNull(entity);
        boolean equality;
        if (entity == any) {
            equality = true;
        } else if (Objects.isNull(any) || !Objects.equals(entity.getClass(), any.getClass())) {
            equality = false;
        } else {
            var other = (E) any;
            equality = this.getters
                    .stream()
                    .allMatch(g -> Objects.equals(g.apply(entity), g.apply(other)));
        }
        return equality;
    }
}
