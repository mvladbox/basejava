package ru.vlad.app.sql;

import ru.vlad.app.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(String sql) {
        execute(sql, p -> {});
    }

    public boolean execute(String sql, SetParams prepareParams) {
        try (Connection conn = connectionFactory.getConnection()) {
            return execute(conn, sql, prepareParams);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    public boolean execute(Connection conn, String sql, SetParams prepareParams) {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            prepareParams.prepare(ps);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    public <K, V> void executeBatch(Connection conn, String sql, Map<K, V> entry, SetBatchParams<K, V> prepareParams) {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Map.Entry<K, V> e : entry.entrySet()) {
                prepareParams.prepare(ps, e);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    public void transactionalExecute(SqlTransaction executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                executor.execute(conn);
                conn.commit();
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
    public interface SetBatchParams<K, V> {
        void prepare(PreparedStatement ps, Map.Entry<K, V> entry) throws SQLException;
    }

    @FunctionalInterface
    public interface GetResult<T> {
        T retrieve(ResultSet rs) throws SQLException;
    }

    @FunctionalInterface
    public interface SqlTransaction {
        void execute(Connection conn) throws SQLException;
    }
}
