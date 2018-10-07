package storage;

import exception.NotExistsStorageException;
import model.Resume;

import java.util.Map;
import java.util.HashMap;

/**
 * Map (HashMap) based storage for Resumes
 */
public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    public int size() {
        return storage.size();
    }

    @Override
    protected void doSave(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object key) {
        storage.replace((String) key, resume);
    }

    @Override
    protected void doDelete(Object key) {
        storage.remove((String) key);
    }

    @Override
    protected String findReference(String uuid) {
        return uuid;
    }

    @Override
    protected boolean existsResumeByReference(Object key) {
        return storage.containsKey(key);
    }

    @Override
    protected Resume doGet(Object key) {
        return storage.get((String) key);
    }
}
