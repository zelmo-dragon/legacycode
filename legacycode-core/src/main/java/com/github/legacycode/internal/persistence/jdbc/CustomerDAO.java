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
        if (this.contains(entity.email())) {

            query = String.format(
                    "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                    CustomerColumn.getTableName(),
                    CustomerColumn.GIVEN_NAME.getColumnName(),
                    CustomerColumn.FAMILY_NAME.getColumnName(),
                    CustomerColumn.EMAIL.getColumnName(),
                    CustomerColumn.GENDER_ID.getColumnName(),
                    CustomerColumn.PHONE_NUMBER.getColumnName(),
                    CustomerColumn.EMAIL.getColumnName()
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
    public void remove(Email key) {
        var query = String.format(
                "DELETE FROM %s WHERE %s = ?",
                CustomerColumn.getTableName(),
                CustomerColumn.EMAIL.getColumnName()
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
    public boolean contains(Email key) {
        var query = String.format(
                "SELECT count(%s) FROM %s WHERE %s = ?",
                CustomerColumn.ID.getColumnName(),
                CustomerColumn.getTableName(),
                CustomerColumn.EMAIL.getColumnName()
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
    public Optional<Customer> find(Email key) {
        var query = String.format(
                "SELECT %s, %s, %s, %s, %s, FROM %s WHERE %s = ?",
                CustomerColumn.GIVEN_NAME.getColumnName(),
                CustomerColumn.FAMILY_NAME.getColumnName(),
                CustomerColumn.EMAIL.getColumnName(),
                CustomerColumn.GENDER_ID.getColumnName(),
                CustomerColumn.PHONE_NUMBER.getColumnName(),
                CustomerColumn.getTableName(),
                CustomerColumn.EMAIL.getColumnName()
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
            s.setObject(1, UUID.randomUUID().toString(), CustomerColumn.ID.getPhysicalType());
            s.setObject(2, e.givenName(), CustomerColumn.GIVEN_NAME.getPhysicalType());
            s.setObject(3, e.familyName(), CustomerColumn.FAMILY_NAME.getPhysicalType());
            s.setObject(4, e.email().value(), CustomerColumn.EMAIL.getPhysicalType());
            s.setObject(5, e.gender().value(), CustomerColumn.GENDER_ID.getPhysicalType());
            s.setObject(6, e.phoneNumber(), CustomerColumn.PHONE_NUMBER.getPhysicalType());
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static void bindUpdateParameter(PreparedStatement s, Customer e) {
        try {
            s.setObject(1, e.givenName(), CustomerColumn.GIVEN_NAME.getPhysicalType());
            s.setObject(2, e.familyName(), CustomerColumn.FAMILY_NAME.getPhysicalType());
            s.setObject(3, e.email().value(), CustomerColumn.EMAIL.getPhysicalType());
            s.setObject(4, e.gender().value(), CustomerColumn.GENDER_ID.getPhysicalType());
            s.setObject(5, e.phoneNumber(), CustomerColumn.PHONE_NUMBER.getPhysicalType());
            s.setObject(5, e.email().value(), CustomerColumn.EMAIL.getPhysicalType());
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static void bindKeyParameter(final PreparedStatement s, final Email e) {
        try {
            s.setObject(1, e.value(), CustomerColumn.EMAIL.getPhysicalType());
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

    private static void bindSelectParameter(final PreparedStatement s, final Email e) {
        try {
            s.setObject(1, e.value(), CustomerColumn.EMAIL.getPhysicalType());
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static Customer extractSelectResult(final ResultSet r) {
        try {
            Customer entity;
            if (r.first()) {

                String givenName = r.getObject(1, CustomerColumn.GIVEN_NAME.getLogicalType());
                String familyName = r.getObject(2, CustomerColumn.FAMILY_NAME.getLogicalType());
                String email = r.getObject(3, CustomerColumn.EMAIL.getLogicalType());
                String genderId = r.getObject(4, CustomerColumn.GENDER_ID.getLogicalType());
                String phoneNumber = r.getObject(5, CustomerColumn.PHONE_NUMBER.getLogicalType());

                entity = new Customer(
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
