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

    public boolean execute(String sql, SetParams prepareParams) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            prepareParams.prepare(ps);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException("");
            }
            throw new StorageException(e);
        }
    }

    public <T> T query(String sql, GetResult<T> result) {
        return query(sql, p -> {}, result);
    }

    public <T> T query(String sql, SetParams prepareParams, GetResult<T> result) {
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
    public interface SetParams {
        void prepare(PreparedStatement ps) throws SQLException;
    }

    @FunctionalInterface
    public interface GetResult<T> {
        T retrieve(ResultSet rs) throws SQLException;
    }
}
