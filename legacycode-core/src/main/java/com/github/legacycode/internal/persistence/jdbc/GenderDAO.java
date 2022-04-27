package com.github.legacycode.internal.persistence.jdbc;

import com.github.legacycode.core.gender.Gender;
import com.github.legacycode.core.gender.GenderRepository;
import com.github.legacycode.core.gender.Name;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@JDBC
public final class GenderDAO implements GenderRepository {

    public GenderDAO() {
    }

    @Override
    public void add(Gender entity) {

        String query;
        Consumer<PreparedStatement> bind;
        if (this.contains(entity.name())) {

            query = String.format(
                    "UPDATE %s SET %s = ?, %s = ? WHERE %s = ?",
                    GenderColumn.getTableName(),
                    GenderColumn.NAME.getColumnName(),
                    GenderColumn.DESCRIPTION.getColumnName(),
                    GenderColumn.NAME.getColumnName()
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
    public void remove(Name key) {
        var query = String.format(
                "DELETE FROM %s WHERE %s = ?",
                GenderColumn.getTableName(),
                GenderColumn.NAME.getColumnName()
        );

        DB.getInstance().executeUpdate(query);
    }

    @Override
    public boolean contains(Name key) {
        var query = String.format(
                "SELECT count(%s) FROM %s WHERE %s = ?",
                GenderColumn.ID.getColumnName(),
                GenderColumn.getTableName(),
                GenderColumn.NAME.getColumnName()
        );

        var count = DB
                .getInstance()
                .execute(
                        query,
                        s -> bindCountParameter(s, key),
                        GenderDAO::extractCountResult
                );

        return count > 0;
    }

    @Override
    public Optional<Gender> find(Name key) {
        var query = String.format(
                "SELECT %s, %s FROM %s WHERE %s = ?",
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
            s.setObject(1, UUID.randomUUID().toString(), GenderColumn.ID.getPhysicalType());
            s.setObject(2, e.name().value(), GenderColumn.NAME.getPhysicalType());
            s.setObject(3, e.description(), GenderColumn.DESCRIPTION.getPhysicalType());
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static void bindUpdateParameter(PreparedStatement s, Gender e) {
        try {
            s.setObject(1, e.name().value(), GenderColumn.NAME.getPhysicalType());
            s.setObject(2, e.description(), GenderColumn.DESCRIPTION.getPhysicalType());
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static void bindCountParameter(final PreparedStatement s, final Name e) {
        try {
            s.setObject(1, e.value(), GenderColumn.NAME.getPhysicalType());
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static long extractCountResult(final ResultSet r) {
        try {
            r.first();
            return r.getLong(1);
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static void bindSelectParameter(final PreparedStatement s, final Name e) {
        try {
            s.setObject(1, e.value(), GenderColumn.NAME.getPhysicalType());
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static Gender extractSelectResult(final ResultSet r) {
        try {
            Gender entity;
            if (r.first()) {

                String name = r.getObject(1, GenderColumn.NAME.getLogicalType());
                String description = r.getObject(2, GenderColumn.DESCRIPTION.getLogicalType());

                entity = new Gender(
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
