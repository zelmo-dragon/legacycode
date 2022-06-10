package com.github.legacycode.repository;

public interface Entity<K> {

    K getId();

    void setId(K id);
}
