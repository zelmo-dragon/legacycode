package com.github.legacycode.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

public interface Repository<E, K> {

    boolean isEmpty();

    boolean contains(E entity);

    long size();

    Optional<E> find(K id);

    List<E> filter(CriteriaPredicate<E, E> criteria);

    E add(E entity);

    void remove(E entity);

    void clear();
}
