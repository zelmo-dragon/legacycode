package com.github.legacycode.internal.persistence.jdbc;

import com.github.legacycode.core.customer.Customer;
import com.github.legacycode.core.gender.Gender;
import com.github.legacycode.core.repository.Identifiable;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Objects;
import java.util.stream.Stream;

enum DynamicColumn {

    CUSTOMER(Customer.class, CustomerColumn.class),
    GENDER(Gender.class, GenderColumn.class);

    private final Class<? extends Identifiable<?>> entityClass;

    private final Class<? extends Column> columnClass;

    DynamicColumn(
            final Class<? extends Identifiable<?>> entityClass,
            final Class<? extends Column> columnClass) {

        this.entityClass = entityClass;
        this.columnClass = columnClass;
    }

    static DynamicColumn of(final Class<? extends Identifiable<?>> entityClass) {
        return Stream
                .of(values())
                .filter(e -> Objects.equals(e.getEntityClass(), entityClass))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Missing enum definition"));
    }

    public String getTableName() {
        return invokeDynamicStatic(this.columnClass, "getTableName");
    }

    public Column getPrimaryKey() {
        return invokeDynamicStatic(this.columnClass, "getPrimaryKey");
    }

    Class<? extends Identifiable<?>> getEntityClass() {
        return entityClass;
    }

    Class<? extends Column> getColumnClass() {
        return columnClass;
    }

    private static <X> X invokeDynamicStatic(
            final Class<?> targetClass,
            final String methodStaticName) {

        try {
            var methodGetTableName = MethodType.methodType(String.class);
            var lookup = MethodHandles.lookup();
            var method = lookup.findStatic(targetClass, methodStaticName, methodGetTableName);
            return (X) method.invoke();
        } catch (Throwable ex) {
            throw new IllegalStateException(ex);
        }
    }
}
