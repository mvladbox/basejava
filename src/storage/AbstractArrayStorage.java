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
        insertIntoArray(resume, (int) index);
        size++;
    }

    @Override
    protected void doUpdate(Resume resume, Object index) {
        storage[(int) index] = resume;
    }

    @Override
    protected void doDelete(Object index) {
        removeFromArray((int) index);
        storage[--size] = null;
    }

    @Override
    protected Resume doGet(Object index) {
        return storage[(int) index];
    }

    @Override
    protected boolean existsResumeByReference(Object index) {
        return (int) index >= 0;
    }

    protected abstract void insertIntoArray(Resume resume, int index);

    protected abstract void removeFromArray(int index);
}
