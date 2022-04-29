package com.github.legacycode.internal.persistence.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import com.github.legacycode.core.gender.Gender;
import com.github.legacycode.core.gender.GenderRepository;
import com.github.legacycode.core.gender.Name;

@JDBC
public final class GenderDAO implements GenderRepository {

    public GenderDAO() {
    }

    @Override
    public void add(Gender entity) {

        String query;
        Consumer<PreparedStatement> bind;
        if (this.contains(entity.getKey())) {

            query = String.format(
                    "UPDATE %s SET %s = ?, %s = ? WHERE %s = ?",
                    GenderColumn.getTableName(),
                    GenderColumn.NAME.getColumnName(),
                    GenderColumn.DESCRIPTION.getColumnName(),
                    GenderColumn.ID.getColumnName()
            );
            bind = s -> bindUpdateParameter(s, entity);
        } else {

            query = String.format(
                    "INSERT INTO %s(%s, %s, %s) VALUES (?, ?, ?)",
                    GenderColumn.getTableName(),
                    GenderColumn.ID.getColumnName(),
                    GenderColumn.NAME.getColumnName(),
                    GenderColumn.DESCRIPTION.getColumnName()
            );

            bind = s -> bindInsertParameter(s, entity);
        }
        DB.getInstance().executeUpdate(query, bind);
    }

    @Override
    public void remove(UUID key) {
        var query = String.format(
                "DELETE FROM %s WHERE %s = ?",
                GenderColumn.getTableName(),
                GenderColumn.ID.getColumnName()
        );

        DB
                .getInstance()
                .execute(
                        query,
                        s -> bindKeyParameter(s, key),
                        GenderDAO::extractLongResult
                );
    }

    @Override
    public boolean contains(UUID key) {
        var query = String.format(
                "SELECT count(%s) FROM %s WHERE %s = ?",
                GenderColumn.ID.getColumnName(),
                GenderColumn.getTableName(),
                GenderColumn.ID.getColumnName()
        );

        var count = DB
                .getInstance()
                .execute(
                        query,
                        s -> bindKeyParameter(s, key),
                        GenderDAO::extractLongResult
                );

        return count > 0;
    }

    @Override
    public Optional<Gender> find(UUID key) {
        var query = String.format(
                "SELECT %s, %s, %s FROM %s WHERE %s = ?",
                GenderColumn.ID.getColumnName(),
                GenderColumn.NAME.getColumnName(),
                GenderColumn.DESCRIPTION.getColumnName(),
                GenderColumn.getTableName(),
                GenderColumn.NAME.getColumnName()
        );

        var entity = DB
                .getInstance()
                .execute(
                        query,
                        s -> bindSelectParameter(s, key),
                        GenderDAO::extractSelectResult
                );

        return Optional.ofNullable(entity);
    }

    private static void bindInsertParameter(PreparedStatement s, Gender e) {
        try {
            s.setObject(1, UUID.randomUUID(), GenderColumn.ID.getPhysicalType());
            s.setObject(2, e.getName().getValue(), GenderColumn.NAME.getPhysicalType());
            s.setObject(3, e.getDescription(), GenderColumn.DESCRIPTION.getPhysicalType());
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static void bindUpdateParameter(PreparedStatement s, Gender e) {
        try {
            s.setObject(1, e.getName().getValue(), GenderColumn.NAME.getPhysicalType());
            s.setObject(2, e.getDescription(), GenderColumn.DESCRIPTION.getPhysicalType());
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static void bindKeyParameter(final PreparedStatement s, final UUID e) {
        try {
            s.setObject(1, e, GenderColumn.ID.getPhysicalType());
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

    private static void bindSelectParameter(final PreparedStatement s, final UUID e) {
        try {
            s.setObject(1, e, GenderColumn.ID.getPhysicalType());
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static Gender extractSelectResult(final ResultSet r) {
        try {
            Gender entity;
            if (r.first()) {

                UUID id = r.getObject(1, GenderColumn.ID.getLogicalType());
                String name = r.getObject(2, GenderColumn.NAME.getLogicalType());
                String description = r.getObject(3, GenderColumn.DESCRIPTION.getLogicalType());

                entity = new Gender(
                        id,
                        new Name(name),
                        description
                );

            } else {
                entity = null;
            }
            return entity;
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
