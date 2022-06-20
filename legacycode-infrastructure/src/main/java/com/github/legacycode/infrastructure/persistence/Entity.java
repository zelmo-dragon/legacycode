package com.github.legacycode.infrastructure.persistence;

public interface Entity<K> {

    K getId();

    void setId(K id);
}
