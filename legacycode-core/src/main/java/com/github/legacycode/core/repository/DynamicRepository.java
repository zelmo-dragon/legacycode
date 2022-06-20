package com.github.legacycode.core.repository;

import java.util.Optional;

public interface DynamicRepository {

    <K, E extends Identifiable<K>> void add(Class<E> entityClass, E entity);

    <K, E extends Identifiable<K>> void remove(Class<E> entityClass, K key);

    <K, E extends Identifiable<K>> boolean contains(Class<E> entityClass, K key);

    <K, E extends Identifiable<K>> Optional<E> find(Class<E> entityClass, K key);

}
