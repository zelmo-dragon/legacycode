package com.github.legacycode.core.common;

import com.github.legacycode.core.Identifiable;
import java.util.Optional;

public interface DynamicRepository {

    <E extends Identifiable<K>, K> void add(Class<E> entityClass, E entity);

    <E extends Identifiable<K>, K> void remove(Class<E> entityClass, K key);

    <E extends Identifiable<K>, K> boolean contains(Class<E> entityClass, K key);

    <E extends Identifiable<K>, K> Optional<E> find(Class<E> entityClass, K key);

}
