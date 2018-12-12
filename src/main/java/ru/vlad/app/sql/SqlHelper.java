package ru.vlad.app.sql;

import ru.vlad.app.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
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
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
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

    public interface SqlTransaction<T> {
        T execute(Connection conn) throws SQLException;
    }
}
