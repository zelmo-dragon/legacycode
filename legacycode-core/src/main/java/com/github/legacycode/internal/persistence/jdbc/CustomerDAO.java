package com.github.legacycode.internal.persistence.jdbc;

import com.github.legacycode.core.customer.Customer;
import com.github.legacycode.core.customer.CustomerRepository;
import com.github.legacycode.core.customer.Email;
import com.github.legacycode.core.customer.GenderId;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@JDBC
public final class CustomerDAO implements CustomerRepository {

    public CustomerDAO() {
    }

    @Override
    public void add(Customer entity) {

        String query;
        Consumer<PreparedStatement> bind;
        if (this.contains(entity.getKey())) {

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

            bind = s -> bindUpdateParameter(s, entity);

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

            bind = s -> bindInsertParameter(s, entity);
        }

        DB.getInstance().executeUpdate(query, bind);

    }

    @Override
    public void remove(UUID key) {
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
    }

    @Override
    public boolean contains(UUID key) {
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
    public Optional<Customer> find(UUID key) {
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

        var entity = DB
                .getInstance()
                .execute(
                        query,
                        s -> bindSelectParameter(s, key),
                        CustomerDAO::extractSelectResult
                );

        return Optional.ofNullable(entity);
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
            s.setObject(5, e.getEmail().getValue(), CustomerColumn.EMAIL.getPhysicalType());
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static void bindKeyParameter(final PreparedStatement s, final UUID e) {
        try {
            s.setObject(1, e, CustomerColumn.ID.getPhysicalType());
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
            s.setObject(1, e, CustomerColumn.ID.getPhysicalType());
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static Customer extractSelectResult(final ResultSet r) {
        try {
            Customer entity;
            if (r.first()) {

                UUID id = r.getObject(1, CustomerColumn.ID.getLogicalType());
                String givenName = r.getObject(2, CustomerColumn.GIVEN_NAME.getLogicalType());
                String familyName = r.getObject(3, CustomerColumn.FAMILY_NAME.getLogicalType());
                String email = r.getObject(4, CustomerColumn.EMAIL.getLogicalType());
                UUID genderId = r.getObject(5, CustomerColumn.GENDER_ID.getLogicalType());
                String phoneNumber = r.getObject(6, CustomerColumn.PHONE_NUMBER.getLogicalType());

                entity = new Customer(
                        id,
                        givenName,
                        familyName,
                        phoneNumber,
                        new Email(email),
                        new GenderId(genderId)
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
