package com.github.legacycode.internal.persistence.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.github.legacycode.core.gender.Gender;
import com.github.legacycode.core.gender.GenderRepository;
import com.github.legacycode.core.gender.Name;
import com.github.legacycode.core.repository.EntityEntry;

@JDBC
public final class GenderDAO implements GenderRepository {

    public GenderDAO() {
    }

    @Override
    public int size() {
        var query = String.format(
                "SELECT count(%s) FROM %s",
                GenderColumn.ID.getColumnName(),
                GenderColumn.getTableName()
        );

        var count = DB
                .getInstance()
                .execute(
                        query,
                        GenderDAO::extractLongResult
                );

        return count.intValue();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(final Object key) {
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
    public boolean containsValue(final Object value) {
        return false;
    }

    @Override
    public Gender get(final Object key) {
        var query = String.format(
                "SELECT %s, %s, %s FROM %s WHERE %s = ?",
                GenderColumn.ID.getColumnName(),
                GenderColumn.NAME.getColumnName(),
                GenderColumn.DESCRIPTION.getColumnName(),
                GenderColumn.getTableName(),
                GenderColumn.NAME.getColumnName()
        );

        return DB
                .getInstance()
                .execute(
                        query,
                        s -> bindSelectParameter(s, key),
                        GenderDAO::extractSelectResult
                );
    }

    @Override
    public Gender put(final UUID key, final Gender value) {
        String query;
        Consumer<PreparedStatement> bind;
        if (this.containsKey(value.getId())) {

            query = String.format(
                    "UPDATE %s SET %s = ?, %s = ? WHERE %s = ?",
                    GenderColumn.getTableName(),
                    GenderColumn.NAME.getColumnName(),
                    GenderColumn.DESCRIPTION.getColumnName(),
                    GenderColumn.ID.getColumnName()
            );
            bind = s -> bindUpdateParameter(s, value);
        } else {

            query = String.format(
                    "INSERT INTO %s(%s, %s, %s) VALUES (?, ?, ?)",
                    GenderColumn.getTableName(),
                    GenderColumn.ID.getColumnName(),
                    GenderColumn.NAME.getColumnName(),
                    GenderColumn.DESCRIPTION.getColumnName()
            );

            bind = s -> bindInsertParameter(s, value);
        }
        DB.getInstance().executeUpdate(query, bind);

        return value;
    }

    @Override
    public Gender remove(final Object key) {
        var entity = this.get(key);
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

        return entity;
    }

    @Override
    public void putAll(final Map<? extends UUID, ? extends Gender> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        var query = String.format(
                "TRUNCATE TABLE %s",
                GenderColumn.getTableName()
        );

        DB
                .getInstance()
                .executeUpdate(query);
    }

    @Override
    public Set<UUID> keySet() {
        var query = String.format(
                "SELECT %s FROM %s",
                GenderColumn.ID.getColumnName(),
                GenderColumn.getTableName()
        );

        return DB
                .getInstance()
                .execute(
                        query,
                        GenderDAO::extractSelectIdResult
                );
    }

    @Override
    public Collection<Gender> values() {
        var query = String.format(
                "SELECT %s, %s, %s FROM %s",
                GenderColumn.ID.getColumnName(),
                GenderColumn.NAME.getColumnName(),
                GenderColumn.DESCRIPTION.getColumnName(),
                GenderColumn.getTableName()
        );

        return DB
                .getInstance()
                .execute(
                        query,
                        GenderDAO::extractSelectAllResult
                );
    }

    @Override
    public Set<Entry<UUID, Gender>> entrySet() {
        return this.values()
                .stream()
                .map(e -> new EntityEntry<>(e.getId(), e))
                .collect(Collectors.toSet());
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
            s.setObject(3, e.getId(), GenderColumn.ID.getPhysicalType());
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static void bindKeyParameter(final PreparedStatement s, final Object e) {
        try {
            s.setObject(1, e, GenderColumn.ID.getPhysicalType());
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static long extractLongResult(final ResultSet r) {
        try {
            r.next();
            return r.getLong(1);
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static void bindSelectParameter(final PreparedStatement s, final Object e) {
        try {
            s.setObject(1, e, GenderColumn.ID.getPhysicalType());
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static Gender extractSelectResult(final ResultSet r) {
        try {
            Gender entity;
            if (r.next()) {

                UUID id = r.getObject(1, GenderColumn.ID.getLogicalType());
                String name = r.getObject(2, GenderColumn.NAME.getLogicalType());
                String description = r.getObject(3, GenderColumn.DESCRIPTION.getLogicalType());

                entity = Gender.of(
                        id,
                        Name.of(name),
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

    private static Set<UUID> extractSelectIdResult(final ResultSet r) {
        try {
            var set = new HashSet<UUID>();
            while (r.next()) {
                UUID id = r.getObject(1, GenderColumn.ID.getLogicalType());
                set.add(id);
            }
            return set;
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static Collection<Gender> extractSelectAllResult(final ResultSet r) {
        try {
            var set = new HashSet<Gender>();
            while (r.next()) {

                UUID id = r.getObject(1, GenderColumn.ID.getLogicalType());
                String name = r.getObject(2, GenderColumn.NAME.getLogicalType());
                String description = r.getObject(3, GenderColumn.DESCRIPTION.getLogicalType());

                var entity = Gender.of(
                        id,
                        Name.of(name),
                        description
                );

                set.add(entity);
            }
            return set;
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

}

