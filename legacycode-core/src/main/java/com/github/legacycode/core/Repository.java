package com.github.legacycode.core;

import java.util.Optional;

public interface Repository<E extends Identifiable<K>, K> {

    void add(E entity);

    void remove(K key);

    boolean contains(K key);

    Optional<E> find(K key);


}
