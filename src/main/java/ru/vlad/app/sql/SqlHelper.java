package ru.vlad.app.sql;

import ru.vlad.app.exception.StorageException;

import java.sql.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public void execute(String sql) {
        execute(sql, p -> {});
    }

    public void execute(String sql, ThrowingConsumer<PreparedStatement> prepareParams) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            prepareParams.accept(ps);
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <R> R query(String sql, ThrowingFunction<ResultSet, R> result) {
        return query(sql, p -> {}, result);
    }

    public <R> R query(String sql, ThrowingConsumer<PreparedStatement> prepareParams, ThrowingFunction<ResultSet, R> result) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            prepareParams.accept(ps);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return result.apply(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    public interface ThrowingConsumer<T> extends Consumer<T> {
        @Override
        default void accept(T t) {
            try {
                acceptThrows(t);
            } catch (SQLException e) {
                throw new StorageException(e);
            }
        }

        void acceptThrows(T t) throws SQLException;
    }

    @FunctionalInterface
    public interface ThrowingFunction<T, R> extends Function<T, R> {
        @Override
        default R apply(T t) {
            try {
                return applyThrows(t);
            } catch (SQLException e) {
                throw new StorageException(e);
            }
        }

        R applyThrows(T t) throws SQLException;
    }
}
