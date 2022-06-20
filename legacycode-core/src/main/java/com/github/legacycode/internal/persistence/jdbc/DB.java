package com.github.legacycode.internal.persistence.jdbc;

import com.github.legacycode.core.LegacyCode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.sql.DataSource;

final class DB {

    private static final DB INSTANCE = new DB();

    private final DataSource database;

    private DB() {
        this.database = LegacyCode.getCurrentContext().get(DataSource.class);
    }

    static DB getInstance() {
        return INSTANCE;
    }

    int executeUpdate(final String query) {
        try (
                var connection = this.database.getConnection();
                var statement = connection.prepareStatement(query)) {

            return statement.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    int executeUpdate(
            final String query,
            final Consumer<PreparedStatement> bind) {

        try (
                var connection = this.database.getConnection();
                var statement = connection.prepareStatement(query)) {
            bind.accept(statement);

            return statement.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    <E> E execute(
            final String query,
            final Consumer<PreparedStatement> bind,
            final Function<ResultSet, E> extractor) {

        try (
                var connection = this.database.getConnection();
                var statement = connection.prepareStatement(query)) {

            bind.accept(statement);
            try (var result = statement.executeQuery()) {
                return extractor.apply(result);
            }
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    <E> E execute(
            final String query,
            final Function<ResultSet, E> extractor) {

        try (
                var connection = this.database.getConnection();
                var statement = connection.prepareStatement(query);
                var result = statement.executeQuery()) {

            return extractor.apply(result);

        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

}
