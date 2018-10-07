package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;

/**
 * Abstract array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int RESUME_MAX_COUNT = 10_000;

    protected final Resume[] storage = new Resume[RESUME_MAX_COUNT];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    @Override
    protected void doSave(Resume resume, Object index) {
        if (size == RESUME_MAX_COUNT) {
            throw new StorageException("Достигнут предел количества сохраняемых резюме (" + RESUME_MAX_COUNT + ")",
                    resume.getUuid());
        }
        insertIntoArray(resume, (Integer) index);
        size++;
    }

    @Override
    protected void doUpdate(Resume resume, Object index) {
        storage[(Integer) index] = resume;
    }

    @Override
    protected void doDelete(Object index) {
        removeFromArray((Integer) index);
        storage[--size] = null;
    }

    @Override
    protected Resume doGet(Object index) {
        return storage[(Integer) index];
    }

    @Override
    protected boolean existsResumeByReference(Object index) {
        return (Integer) index >= 0;
    }

    protected abstract Integer findReference(String uuid);

    protected abstract void insertIntoArray(Resume resume, Integer index);

    protected abstract void removeFromArray(Integer index);
}
