package com.github.legacycode.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public final class LegacyCode {

    private static final LegacyCode INSTANCE = new LegacyCode();

    private final Map<Class<?>, Object> context;

    private Function<Class<?>, Object> contextResolver;

    private LegacyCode() {
        this.context = new HashMap<>();
        this.contextResolver = LegacyCode::setVoidResolver;
    }

    public static LegacyCode getCurrentContext() {
        return INSTANCE;
    }

    public void setResolver(final Function<Class<?>, Object> contextResolver) {
        this.contextResolver = Objects.requireNonNull(contextResolver);
    }

    public void set(final Map<Class<?>, Object> newContext) {
        Objects.requireNonNull(newContext);
        this.context.clear();
        this.context.putAll(newContext);
    }

    public void clear() {
        this.context.clear();
    }

    public void put(final Class<?> targetClass, final Object instance) {
        this.context.put(targetClass, instance);
    }

    public <I> I get(final Class<I> targetClass) {

        return (I) this.context.getOrDefault(
                targetClass,
                contextResolver.apply(targetClass)
        );
    }

    private static Object setVoidResolver(Class<?> providerClass) {
        throw new UnsupportedOperationException("No resolver bind");
    }

}
