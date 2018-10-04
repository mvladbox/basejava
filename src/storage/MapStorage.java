package storage;

import exception.NotExistsStorageException;
import model.Resume;

import java.util.HashMap;

/**
 * Map (HashMap) based storage for Resumes
 */
public class MapStorage extends AbstractStorage {
    private final HashMap<String, Resume> storage = new HashMap<String, Resume>();

    public void clear() {
        storage.clear();
    }

    @Override
    public void delete(String uuid) {
        Resume resume = storage.remove(uuid);
        if (resume == null) {
            throw new NotExistsStorageException(uuid);
        }
    }

    @Override
    public Resume get(String uuid) {
        Resume resume = storage.get(uuid);
        if (resume == null) {
            throw new NotExistsStorageException(uuid);
        }
        return resume;
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    public int size() {
        return storage.size();
    }

    @Override
    protected void doSave(Resume resume, int index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doUpdate(Resume resume, int index) {
        storage.replace(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(int index) {
        /* Unused. Retrieve by index not need (not supported) */
    }

    @Override
    protected int getIndex(String uuid) {
        return (storage.get(uuid) == null) ? -1 : 0;
    }

    @Override
    protected Resume getResume(int index) {
        /* Unused. Retrieve by index not need (not supported) */
        return null;
    }
}
