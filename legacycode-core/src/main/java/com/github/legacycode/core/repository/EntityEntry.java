package com.github.legacycode.core.repository;

import java.util.Map;

public final class EntityEntry<K, E> implements Map.Entry<K, E> {

    private final K key;

    private final E value;

   public EntityEntry(final K key, final E value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public E getValue() {
        return this.value;
    }

    @Override
    public E setValue(final E value) {
        throw new UnsupportedOperationException("Instance is not mutable !");
    }
}
