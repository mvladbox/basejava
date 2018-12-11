package ru.vlad.app.sql;

import ru.vlad.app.exception.ExistStorageException;
import ru.vlad.app.exception.StorageException;

import java.sql.*;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public void execute(String sql) {
        execute(sql, p -> {});
    }

    public int execute(String sql, SetParams<PreparedStatement> prepareParams) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            prepareParams.prepare(ps);
            return ps.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException("");
            }
            throw new StorageException(e);
        }
    }

    public <R> R query(String sql, GetResult<ResultSet, R> result) {
        return query(sql, p -> {}, result);
    }

    public <R> R query(String sql, SetParams<PreparedStatement> prepareParams, GetResult<ResultSet, R> result) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            prepareParams.prepare(ps);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return result.retrieve(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    public interface SetParams<T> {
        void prepare(T t) throws SQLException;
    }

    @FunctionalInterface
    public interface GetResult<T, R> {
        R retrieve(T t) throws SQLException;
    }
}
