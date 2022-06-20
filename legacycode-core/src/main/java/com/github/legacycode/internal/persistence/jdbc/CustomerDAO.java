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

import com.github.legacycode.core.customer.Customer;
import com.github.legacycode.core.customer.CustomerRepository;
import com.github.legacycode.core.customer.Email;
import com.github.legacycode.core.customer.GenderId;
import com.github.legacycode.core.repository.EntityEntry;

@JDBC
public final class CustomerDAO implements CustomerRepository {

    public CustomerDAO() {
    }


    @Override
    public int size() {
        var query = String.format(
                "SELECT count(%s) FROM %s",
                CustomerColumn.ID.getColumnName(),
                CustomerColumn.getTableName()
        );

        var count = DB
                .getInstance()
                .execute(
                        query,
                        CustomerDAO::extractLongResult
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
                CustomerColumn.ID.getColumnName(),
                CustomerColumn.getTableName(),
                CustomerColumn.ID.getColumnName()
        );

        var count = DB
                .getInstance()
                .execute(
                        query,
                        s -> bindKeyParameter(s, key),
                        CustomerDAO::extractLongResult
                );

        return count > 0;
    }

    @Override
    public boolean containsValue(final Object value) {
        return false;
    }

    @Override
    public Customer get(final Object key) {
        var query = String.format(
                "SELECT %s, %s, %s, %s, %s, %s, FROM %s WHERE %s = ?",
                CustomerColumn.ID.getColumnName(),
                CustomerColumn.GIVEN_NAME.getColumnName(),
                CustomerColumn.FAMILY_NAME.getColumnName(),
                CustomerColumn.EMAIL.getColumnName(),
                CustomerColumn.GENDER_ID.getColumnName(),
                CustomerColumn.PHONE_NUMBER.getColumnName(),
                CustomerColumn.getTableName(),
                CustomerColumn.ID.getColumnName()
        );

        return DB
                .getInstance()
                .execute(
                        query,
                        s -> bindSelectParameter(s, key),
                        CustomerDAO::extractSelectResult
                );
    }

    @Override
    public Customer put(final UUID key, final Customer value) {
        String query;
        Consumer<PreparedStatement> bind;
        if (this.containsKey(value.getId())) {

            query = String.format(
                    "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                    CustomerColumn.getTableName(),
                    CustomerColumn.GIVEN_NAME.getColumnName(),
                    CustomerColumn.FAMILY_NAME.getColumnName(),
                    CustomerColumn.EMAIL.getColumnName(),
                    CustomerColumn.GENDER_ID.getColumnName(),
                    CustomerColumn.PHONE_NUMBER.getColumnName(),
                    CustomerColumn.ID.getColumnName()
            );

            bind = s -> bindUpdateParameter(s, value);

        } else {

            query = String.format(
                    "INSERT INTO %s(%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?)",
                    CustomerColumn.getTableName(),
                    CustomerColumn.ID.getColumnName(),
                    CustomerColumn.GIVEN_NAME.getColumnName(),
                    CustomerColumn.FAMILY_NAME.getColumnName(),
                    CustomerColumn.EMAIL.getColumnName(),
                    CustomerColumn.GENDER_ID.getColumnName(),
                    CustomerColumn.PHONE_NUMBER.getColumnName()
            );

            bind = s -> bindInsertParameter(s, value);
        }

        DB.getInstance().executeUpdate(query, bind);

        return value;
    }

    @Override
    public Customer remove(final Object key) {
        var entity = this.get(key);

        var query = String.format(
                "DELETE FROM %s WHERE %s = ?",
                CustomerColumn.getTableName(),
                CustomerColumn.ID.getColumnName()
        );

        DB
                .getInstance()
                .execute(
                        query,
                        s -> bindKeyParameter(s, key),
                        CustomerDAO::extractLongResult
                );

        return entity;
    }

    @Override
    public void putAll(final Map<? extends UUID, ? extends Customer> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        var query = String.format(
                "TRUNCATE TABLE %s",
                CustomerColumn.getTableName()
        );

        DB
                .getInstance()
                .executeUpdate(query);
    }

    @Override
    public Set<UUID> keySet() {

        var query = String.format(
                "SELECT %s FROM %s",
                CustomerColumn.ID.getColumnName(),
                CustomerColumn.getTableName()
        );

        return DB
                .getInstance()
                .execute(
                        query,
                        CustomerDAO::extractSelectIdResult
                );
    }

    @Override
    public Collection<Customer> values() {
        var query = String.format(
                "SELECT %s, %s, %s, %s, %s, %s, FROM %s",
                CustomerColumn.ID.getColumnName(),
                CustomerColumn.GIVEN_NAME.getColumnName(),
                CustomerColumn.FAMILY_NAME.getColumnName(),
                CustomerColumn.EMAIL.getColumnName(),
                CustomerColumn.GENDER_ID.getColumnName(),
                CustomerColumn.PHONE_NUMBER.getColumnName(),
                CustomerColumn.getTableName()
        );

        return DB
                .getInstance()
                .execute(
                        query,
                        CustomerDAO::extractSelectAllResult
                );
    }

    @Override
    public Set<Entry<UUID, Customer>> entrySet() {
        return this.values()
                .stream()
                .map(e -> new EntityEntry<>(e.getId(), e))
                .collect(Collectors.toSet());
    }


    private static void bindInsertParameter(PreparedStatement s, Customer e) {
        try {
            s.setObject(1, UUID.randomUUID(), CustomerColumn.ID.getPhysicalType());
            s.setObject(2, e.getGivenName(), CustomerColumn.GIVEN_NAME.getPhysicalType());
            s.setObject(3, e.getFamilyName(), CustomerColumn.FAMILY_NAME.getPhysicalType());
            s.setObject(4, e.getEmail().getValue(), CustomerColumn.EMAIL.getPhysicalType());
            s.setObject(5, e.getGender().getId(), CustomerColumn.GENDER_ID.getPhysicalType());
            s.setObject(6, e.getPhoneNumber(), CustomerColumn.PHONE_NUMBER.getPhysicalType());
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static void bindUpdateParameter(PreparedStatement s, Customer e) {
        try {
            s.setObject(1, e.getGivenName(), CustomerColumn.GIVEN_NAME.getPhysicalType());
            s.setObject(2, e.getFamilyName(), CustomerColumn.FAMILY_NAME.getPhysicalType());
            s.setObject(3, e.getEmail().getValue(), CustomerColumn.EMAIL.getPhysicalType());
            s.setObject(4, e.getGender().getId(), CustomerColumn.GENDER_ID.getPhysicalType());
            s.setObject(5, e.getPhoneNumber(), CustomerColumn.PHONE_NUMBER.getPhysicalType());
            s.setObject(6, e.getId(), CustomerColumn.ID.getPhysicalType());
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static void bindKeyParameter(final PreparedStatement s, final Object e) {
        try {
            s.setObject(1, e, CustomerColumn.ID.getPhysicalType());
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
            s.setObject(1, e, CustomerColumn.ID.getPhysicalType());
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static Customer extractSelectResult(final ResultSet r) {
        try {
            Customer entity;
            if (r.next()) {

                UUID id = r.getObject(1, CustomerColumn.ID.getLogicalType());
                String givenName = r.getObject(2, CustomerColumn.GIVEN_NAME.getLogicalType());
                String familyName = r.getObject(3, CustomerColumn.FAMILY_NAME.getLogicalType());
                String email = r.getObject(4, CustomerColumn.EMAIL.getLogicalType());
                UUID genderId = r.getObject(5, CustomerColumn.GENDER_ID.getLogicalType());
                String phoneNumber = r.getObject(6, CustomerColumn.PHONE_NUMBER.getLogicalType());

                entity = Customer.of(
                        id,
                        givenName,
                        familyName,
                        phoneNumber,
                        Email.of(email),
                        GenderId.of(genderId)
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
                UUID id = r.getObject(1, CustomerColumn.ID.getLogicalType());
                set.add(id);
            }
            return set;
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static Collection<Customer> extractSelectAllResult(final ResultSet r) {
        try {
            var set = new HashSet<Customer>();
            while (r.next()) {

                UUID id = r.getObject(1, CustomerColumn.ID.getLogicalType());
                String givenName = r.getObject(2, CustomerColumn.GIVEN_NAME.getLogicalType());
                String familyName = r.getObject(3, CustomerColumn.FAMILY_NAME.getLogicalType());
                String email = r.getObject(4, CustomerColumn.EMAIL.getLogicalType());
                UUID genderId = r.getObject(5, CustomerColumn.GENDER_ID.getLogicalType());
                String phoneNumber = r.getObject(6, CustomerColumn.PHONE_NUMBER.getLogicalType());

                var entity = Customer.of(
                        id,
                        givenName,
                        familyName,
                        phoneNumber,
                        Email.of(email),
                        GenderId.of(genderId)
                );

                set.add(entity);
            }
            return set;
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

}
