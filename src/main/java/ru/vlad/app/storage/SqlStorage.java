package ru.vlad.app.storage;

import ru.vlad.app.exception.NotExistStorageException;
import ru.vlad.app.model.*;
import ru.vlad.app.sql.SqlHelper;
import ru.vlad.app.util.JsonParser;

import java.sql.*;
import java.util.*;

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
            saveSections(resume, conn);
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
            sqlHelper.execute(conn, "DELETE FROM section WHERE resume_uuid = ?", ps -> ps.setString(1, resume.getUuid()));
            saveContacts(resume, conn);
            saveSections(resume, conn);
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
        return sqlHelper.batchQuery(conn -> {
            Resume resume = sqlHelper.query(conn, "SELECT * FROM resume WHERE uuid = ?",
                    ps -> ps.setString(1, uuid),
                    rs -> new Resume(uuid, rs.getString("full_name")));
            if (resume == null) {
                throw new NotExistStorageException(uuid);
            }
            sqlHelper.query(conn, "SELECT * FROM contact WHERE resume_uuid = ?",
                    ps -> ps.setString(1, uuid),
                    rs -> loadContacts(resume, rs));
            sqlHelper.query(conn, "SELECT * FROM section WHERE resume_uuid = ?",
                    ps -> ps.setString(1, uuid),
                    rs -> loadSections(resume, rs));
            return resume;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.batchQuery(conn -> {
            Map<String, Resume> resumes = sqlHelper.query(conn, "SELECT * FROM resume ORDER BY full_name", rs -> {
                Map<String, Resume> map = new LinkedHashMap<>();
                do {
                    String uuid = rs.getString("uuid");
                    map.put(uuid, new Resume(uuid, rs.getString("full_name")));
                } while (rs.next());
                return map;
            });
            sqlHelper.query(conn, "SELECT * FROM contact ORDER BY resume_uuid",
                    rs -> loadContacts(resumes.get(rs.getString("resume_uuid")), rs));
            sqlHelper.query(conn, "SELECT * FROM section ORDER BY resume_uuid",
                    rs -> loadSections(resumes.get(rs.getString("resume_uuid")), rs));

            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.query("SELECT COUNT(*) FROM resume", rs -> rs.getInt(1));
    }

    private void saveContacts(Resume resume, Connection conn) throws SQLException {
        sqlHelper.executeBatch(conn, "INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)", resume.getContacts(), (ps, e) -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, e.getKey().name());
            ps.setString(3, e.getValue().getValue());
        });
    }

    private void saveSections(Resume resume, Connection conn) throws SQLException {
        sqlHelper.executeBatch(conn, "INSERT INTO section (resume_uuid, type, value) VALUES (?, ?, ?)", resume.getSections(), (ps, e) -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, e.getKey().name());
            ps.setString(3, convertSectionToString(e.getValue()));
        });
    }

    private Resume loadContacts(Resume resume, ResultSet rs) throws SQLException {
        do {
            if (rs.getString("type") != null) {
                resume.addContact(new Contact(ContactType.valueOf(rs.getString("type")), rs.getString("value")));
            }
        } while (rs.next() && resume.getUuid().equals(rs.getString("resume_uuid")));
        return resume;
    }

    private Resume loadSections(Resume resume, ResultSet rs) throws SQLException {
        do {
            if (rs.getString("type") != null) {
                resume.addSection(SectionType.valueOf(rs.getString("type")), convertStringToSection(rs.getString("value")));
            }
        } while (rs.next() && resume.getUuid().equals(rs.getString("resume_uuid")));
        return resume;
    }

// ------- Functional style -------
//    private Resume loadContacts(Resume resume, ResultSet rs) throws SQLException {
//        return load(resume, rs, (type) -> resume.addContact(new Contact(ContactType.valueOf(type), rs.getString("value"))));
//    }
//
//    private Resume loadSections(Resume resume, ResultSet rs) throws SQLException {
//        return load(resume, rs, (type) -> resume.addSection(SectionType.valueOf(type), convertStringToSection(rs.getString("value"))));
//    }
//
//    private Resume load(Resume resume, ResultSet rs, Load loader) throws SQLException {
//        do {
//            if (rs.getString("type") != null) {
//                loader.load(rs.getString("type"));
//            }
//        } while (rs.next() && resume.getUuid().equals(rs.getString("resume_uuid")));
//        return resume;
//    }
//
//    @FunctionalInterface
//    public interface Load {
//        void load(String type) throws SQLException;
//    }

    private String convertSectionToString(AbstractSection section) {
        return JsonParser.write(section, AbstractSection.class);
    }

    private AbstractSection convertStringToSection(String value) {
        return JsonParser.read(value, AbstractSection.class);
    }
}
