package com.github.legacycode.jakarta.persistence;

public interface Entity<K> {

    K getId();

    void setId(K id);
}
