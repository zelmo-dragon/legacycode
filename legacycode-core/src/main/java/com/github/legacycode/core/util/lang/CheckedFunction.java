package com.github.legacycode.core.util.lang;

import java.util.function.Function;

@FunctionalInterface
public interface CheckedFunction<T, R> {

    R apply(T param) throws Exception;

    static <T, R> Function<T, R> wrap(final CheckedFunction<T, R> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
        };
    }
}
