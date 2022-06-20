package com.github.legacycode.internal.persistence.jdbc;

import com.github.legacycode.core.repository.DynamicRepository;
import com.github.legacycode.core.repository.Identifiable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@JDBC
public final class DynamicDAO implements DynamicRepository {

    private static final String TABLE_NAME = ":tableName";

    private static final String KEY_COLUMN = ":pkColumn";

    public DynamicDAO() {
    }

    @Override
    public <K, E extends Identifiable<K>> void add(
            final Class<E> entityClass,
            final E entity) {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <K, E extends Identifiable<K>> void remove(
            final Class<E> entityClass,
            final K key) {

        var queryTemplate = String.format(
                "DELETE FROM %s WHERE %s = ?",
                TABLE_NAME,
                KEY_COLUMN
        );

        var tableDefinition = DynamicColumn.of(entityClass);
        var tableName = tableDefinition.getTableName();
        var keyColumnName = tableDefinition.getPrimaryKey().getColumnName();
        var query = queryTemplate
                .replace(TABLE_NAME, tableName)
                .replace(KEY_COLUMN, keyColumnName);

        DB
                .getInstance()
                .execute(
                        query,
                        s -> bindKeyParameter(s, tableDefinition.getPrimaryKey(), key),
                        DynamicDAO::extractLongResult
                );
    }

    @Override
    public <K, E extends Identifiable<K>> boolean contains(
            final Class<E> entityClass,
            final K key) {

        var queryTemplate = String.format(
                "SELECT count(%s) FROM %s WHERE %s = ?",
                KEY_COLUMN,
                TABLE_NAME,
                KEY_COLUMN
        );

        var tableDefinition = DynamicColumn.of(entityClass);
        var tableName = tableDefinition.getTableName();
        var keyColumnName = tableDefinition.getPrimaryKey().getColumnName();
        var query = queryTemplate
                .replace(TABLE_NAME, tableName)
                .replace(KEY_COLUMN, keyColumnName);

        var count = DB
                .getInstance()
                .execute(
                        query,
                        s -> bindKeyParameter(s, tableDefinition.getPrimaryKey(), key),
                        DynamicDAO::extractLongResult
                );

        return count > 0;
    }

    @Override
    public <K, E extends Identifiable<K>> Optional<E> find(
            final Class<E> entityClass,
            final K key) {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static void bindKeyParameter(
            final PreparedStatement s,
            final Column column,
            final Object value) {

        try {
            s.setObject(1, value, column.getPhysicalType());
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static long extractLongResult(final ResultSet r) {
        try {
            r.first();
            return r.getLong(1);
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
