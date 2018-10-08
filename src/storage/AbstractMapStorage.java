package storage;

import model.Resume;

import java.util.HashMap;
import java.util.Map;

/**
 * Map (HashMap) based storage for Resumes
 */
public abstract class AbstractMapStorage extends AbstractStorage {
    protected final Map<String, Resume> map = new HashMap<>();

    public void clear() {
        map.clear();
    }

    @Override
    public Resume[] getAll() {
        return map.values().toArray(new Resume[0]);
    }

    public int size() {
        return map.size();
    }

    @Override
    protected void doSave(Resume resume, Object key) {
        map.put(getKey(resume), resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object key) {
        map.replace((String) key, resume);
    }

    @Override
    protected void doDelete(Object key) {
        map.remove((String) key);
    }

    @Override
    protected boolean existsResumeByReference(Object key) {
        return map.containsKey((String) key);
    }

    @Override
    protected Resume doGet(Object key) {
        return map.get((String) key);
    }

    protected abstract String getKey(Resume resume);
}
