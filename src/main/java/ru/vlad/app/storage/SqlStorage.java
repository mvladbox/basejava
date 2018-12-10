package ru.vlad.app.storage;

import ru.vlad.app.exception.ExistStorageException;
import ru.vlad.app.exception.NotExistStorageException;
import ru.vlad.app.model.Resume;
import ru.vlad.app.sql.SqlHelper;

import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void save(Resume resume) {
        try {
            sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?, ?)", p -> {
                p.setString(1, resume.getUuid());
                p.setString(2, resume.getFullName());
            });
        } catch (Exception e) {
            if (get(resume.getUuid()) != null) {
                throw new ExistStorageException(resume.getUuid());
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void update(Resume resume) {
        if (get(resume.getUuid()) == null) {
            throw new NotExistStorageException(resume.getUuid());
        }
        sqlHelper.execute("UPDATE resume SET full_name = ? WHERE uuid = ?", p -> {
            p.setString(1, resume.getFullName());
            p.setString(2, resume.getUuid());
        });
    }

    @Override
    public void delete(String uuid) {
        if (get(uuid) == null) {
            throw new NotExistStorageException(uuid);
        }
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", p -> p.setString(1, uuid));
    }

    @Override
    public Resume get(String uuid) {
        Resume resume = sqlHelper.query("SELECT * FROM resume r WHERE r.uuid = ?",
                p -> p.setString(1, uuid),
                r -> new Resume(uuid, r.getString("full_name")));
        if (resume == null) {
            throw new NotExistStorageException(uuid);
        }
        return resume;
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.query("SELECT * FROM resume r ORDER BY r.full_name",
                r -> {
                    List<Resume> list = new ArrayList<>();
                    do {
                        list.add(new Resume(r.getString("uuid").trim(), r.getString("full_name")));
                    } while (r.next());
                    return list;
                });
    }

    @Override
    public int size() {
        return sqlHelper.query("SELECT COUNT(*) cnt FROM resume", r -> r.getInt("cnt"));
    }
}
