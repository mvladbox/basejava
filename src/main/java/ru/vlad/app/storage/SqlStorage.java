package ru.vlad.app.storage;

import ru.vlad.app.exception.NotExistStorageException;
import ru.vlad.app.model.Contact;
import ru.vlad.app.model.ContactType;
import ru.vlad.app.model.Resume;
import ru.vlad.app.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
//        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?, ?)", p -> {
//            p.setString(1, resume.getUuid());
//            p.setString(2, resume.getFullName());
//        });
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
                        for (Map.Entry<ContactType, Contact> e : resume.getContacts().entrySet()) {
                            ps.setString(1, resume.getUuid());
                            ps.setString(2, e.getKey().name());
                            ps.setString(3, e.getValue().getValue());
                            ps.addBatch();
                        }
                        ps.executeBatch();
                    }
                    return null;
                }
        );
    }

    @Override
    public void update(Resume resume) {
        if (!sqlHelper.execute("UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
        })) {
            throw new NotExistStorageException(resume.getUuid());
        }
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
                rs -> {
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        ContactType type = ContactType.valueOf(rs.getString("type"));
                        String value = rs.getString("value");
                        r.addContact(new Contact(type, value));
                    } while (rs.next());
                    return r;
                });
        if (resume == null) {
            throw new NotExistStorageException(uuid);
        }
        return resume;
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.query("SELECT * FROM resume r ORDER BY r.full_name, r.uuid", rs -> {
            List<Resume> list = new ArrayList<>();
            do {
                list.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            } while (rs.next());
            return list;
        });
    }

    @Override
    public int size() {
        return sqlHelper.query("SELECT COUNT(*) FROM resume", rs -> rs.getInt(1));
    }
}
