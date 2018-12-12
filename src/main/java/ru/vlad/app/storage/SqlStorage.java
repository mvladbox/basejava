package ru.vlad.app.storage;

import ru.vlad.app.exception.NotExistStorageException;
import ru.vlad.app.model.Contact;
import ru.vlad.app.model.ContactType;
import ru.vlad.app.model.Resume;
import ru.vlad.app.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            sqlHelper.execute(conn, "INSERT INTO resume (uuid, full_name) VALUES (?, ?)", ps -> {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
            });
            saveContacts(resume, conn);
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            if (!sqlHelper.execute(conn, "UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
            })) {
                throw new NotExistStorageException(resume.getUuid());
            }
            sqlHelper.execute(conn, "DELETE FROM contact WHERE resume_uuid = ?", ps -> ps.setString(1, resume.getUuid()));
            saveContacts(resume, conn);
        });

    }

    @Override
    public void delete(String uuid) {
        if (!sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", ps -> ps.setString(1, uuid))) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume get(String uuid) {
        Resume resume = sqlHelper.query("" +
                        "SELECT *" +
                        "  FROM resume r" +
                        "       LEFT JOIN contact c ON c.resume_uuid = r.uuid" +
                        " WHERE r.uuid = ?",
                ps -> ps.setString(1, uuid),
                rs -> loadContacts(new Resume(uuid, rs.getString("full_name")), rs));
        if (resume == null) {
            throw new NotExistStorageException(uuid);
        }
        return resume;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = sqlHelper.query("" +
                        "SELECT *" +
                        "  FROM resume r" +
                        "       LEFT JOIN contact c ON c.resume_uuid = r.uuid" +
                        " ORDER BY r.full_name, r.uuid",
                rs -> {
                    List<Resume> resumes = new ArrayList<>();
                    do {
                        resumes.add(loadContacts(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")), rs));
                    } while (!rs.isAfterLast());
                    return resumes;
                });
        if (list == null) {
            list = Collections.emptyList();
        }
        return list;
    }

    @Override
    public int size() {
        return sqlHelper.query("SELECT COUNT(*) FROM resume", rs -> rs.getInt(1));
    }

    private void saveContacts(Resume resume, Connection conn) {
        sqlHelper.executeBatch(conn, "INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)", resume.getContacts(), (ps, e) -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, e.getKey().name());
            ps.setString(3, e.getValue().getValue());
        });

    }

    private Resume loadContacts(Resume resume, ResultSet rs) throws SQLException {
        do {
            if (rs.getString("type") != null) {
                resume.addContact(new Contact(ContactType.valueOf(rs.getString("type")), rs.getString("value")));
            }
        } while (rs.next() && resume.getUuid().equals(rs.getString("uuid").trim()));
        return resume;
    }
}
