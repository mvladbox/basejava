package ru.vlad.app.sql;

import org.postgresql.util.PSQLException;
import ru.vlad.app.exception.ExistStorageException;
import ru.vlad.app.exception.StorageException;

import java.sql.SQLException;

public class ExceptionUtil {

    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException) {
            if (e.getSQLState().equals("23505")) {
                return new ExistStorageException(null);
            }
        }
        return new StorageException(e);
    }
}
