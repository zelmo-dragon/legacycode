package com.github.legacycode.jakarta.persistence;

import java.util.Optional;

public interface DAO<E extends Entity<K>, K> {

    E add(E entity);

    void remove(E entity);

    void remove(K key);

    boolean contains(E entity);

    Optional<E> find(K key);
}
