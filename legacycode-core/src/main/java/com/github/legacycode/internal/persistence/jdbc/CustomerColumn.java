package com.github.legacycode.internal.persistence.jdbc;

import java.sql.Types;

enum CustomerColumn implements Column {

    ID("id", String.class, Types.VARCHAR),
    GIVEN_NAME("given_name", String.class, Types.VARCHAR),
    FAMILY_NAME("family_name", String.class, Types.VARCHAR),
    EMAIL("email", String.class, Types.VARCHAR),
    GENDER_ID("gender_id", String.class, Types.VARCHAR),
    PHONE_NUMBER("phone_number", String.class, Types.VARCHAR);

    private final String columnName;

    private final Class<?> logicalType;

    private final int physicalType;

    CustomerColumn(
            final String columnName,
            final Class<?> logicalType,
            final int physicalType) {

        this.columnName = columnName;
        this.logicalType = logicalType;
        this.physicalType = physicalType;
    }

    static String getTableName() {
        return "customer";
    }

    static CustomerColumn getPrimaryKey() {
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
