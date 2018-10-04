package storage;

import exception.StorageException;
import exception.ExistsStorageException;
import exception.NotExistsStorageException;
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

    @Override
    public void save(Resume resume) {
        if (size == RESUME_MAX_COUNT) {
            throw new StorageException("Достигнут предел количества сохраняемых резюме (" + RESUME_MAX_COUNT + ")",
                    resume.getUuid());
        }
        super.save(resume);
        size++;
    }

    @Override
    public void delete(String uuid) {
        super.delete(uuid);
        storage[--size] = null;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    @Override
    protected void doUpdate(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected Resume getResume(int index) {
        return storage[index];
    }
}
