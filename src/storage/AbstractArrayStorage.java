package storage;

import exception.StorageException;
import exception.ExistsStorageException;
import exception.NotExistsStorageException;
import model.Resume;

import java.util.Arrays;

/**
 * Abstract array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int RESUME_MAX_COUNT = 10_000;

    protected final Resume[] storage = new Resume[RESUME_MAX_COUNT];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size == RESUME_MAX_COUNT) {
            throw new StorageException("Достигнут предел количества сохраняемых резюме (" + RESUME_MAX_COUNT + ")",
                    resume.getUuid());
        }
        final int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistsStorageException(resume.getUuid());
        }
        doSave(resume, index);
        size++;
    }

    public void update(Resume resume) {
        final int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new ExistsStorageException(resume.getUuid());
        }
        storage[index] = resume;
    }

    public void delete(String uuid) {
        final int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistsStorageException(uuid);
        }
        doDelete(index);
        storage[--size] = null;
    }

    public Resume get(String uuid) {
        final int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistsStorageException(uuid);
        }
        return storage[index];
    }

    public int size() {
        return size;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void doSave(Resume resume, int index);

    protected abstract void doDelete(int index);
}
