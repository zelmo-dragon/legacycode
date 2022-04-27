package com.github.legacycode.internal.persistence.jdbc;

interface Column {

    String getColumnName();

    <T> Class<T> getLogicalType();

    int getPhysicalType();

}
