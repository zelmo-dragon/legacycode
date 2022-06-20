package com.github.legacycode.internal.persistence.collection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

final class DB {

    private static final DB INSTANCE = new DB();

    private final Map<Class<?>, Set<Object>> database;

    private DB() {
        this.database = new HashMap<>();
    }

    static DB getInstance() {
        return INSTANCE;
    }

    <E> E put(final Class<E> entityClass, final Set<E> data) {
        var internalData = this.database.getOrDefault(entityClass, new HashSet<>(data.size()));
        internalData.addAll(data);
        return (E) this.database.put(entityClass, internalData);
    }

    <E> void remove(final Class<E> entityClass) {
        this.database.remove(entityClass);
    }

    <E> void remove(final Class<E> entityClass, final E entity) {
        var internalData = this.database.getOrDefault(entityClass, Set.of());
        internalData.remove(entity);
    }

    <E> void removeIf(final Class<E> entityClass, final Predicate<E> filter) {
        var internalData = (Set<E>) this.database.getOrDefault(entityClass, new HashSet<>());
        internalData.removeIf(filter);
    }

    <E> Set<E> get(final Class<E> entityClass) {
        var internalData = (Set<E>) this.database.getOrDefault(entityClass, Set.of());
        return Set.copyOf(internalData);
    }

    <E> Optional<E> get(final Class<E> entityClass, final Predicate<E> filter) {
        return this.database
                .getOrDefault(entityClass, Set.of())
                .stream()
                .map(e -> (E) e)
                .filter(filter)
                .findFirst();
    }

    <E> boolean contains(final Class<E> entityClass, final Predicate<E> filter) {
        return this.database
                .getOrDefault(entityClass, Set.of())
                .stream()
                .map(e -> (E) e)
                .anyMatch(filter);
    }

    void clear() {
        this.database.clear();
    }
}
