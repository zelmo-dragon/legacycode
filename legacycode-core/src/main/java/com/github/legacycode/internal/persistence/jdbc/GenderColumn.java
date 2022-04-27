package com.github.legacycode.internal.persistence.jdbc;

import java.sql.Types;

enum GenderColumn implements Column {

    ID("id", String.class, Types.VARCHAR),
    NAME("name", String.class, Types.VARCHAR),
    DESCRIPTION("description", String.class, Types.VARCHAR);

    private final String columnName;

    private final Class<?> logicalType;

    private final int physicalType;

    GenderColumn(
            final String columnName,
            final Class<?> logicalType,
            final int physicalType) {

        this.columnName = columnName;
        this.logicalType = logicalType;
        this.physicalType = physicalType;
    }

    static String getTableName() {
        return "gender";
    }

    static GenderColumn getPrimaryKey() {
        return ID;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public <T> Class<T> getLogicalType() {
        return (Class<T>) logicalType;
    }

    @Override
    public int getPhysicalType() {
        return physicalType;
    }

}
