package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Abstract array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int RESUME_MAX_COUNT = 10_000;

    protected final Resume[] array = new Resume[RESUME_MAX_COUNT];
    protected int size;

    public void clear() {
        Arrays.fill(array, 0, size, null);
        size = 0;
    }

    @Override
    public List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOfRange(array, 0, size));
    }

    public int size() {
        return size;
    }

    @Override
    protected void doSave(Resume resume, Integer index) {
        if (size == RESUME_MAX_COUNT) {
            throw new StorageException("Достигнут предел количества сохраняемых резюме (" + RESUME_MAX_COUNT + ")",
                    resume.getUuid());
        }
        insertIntoArray(resume, index);
        size++;
    }

    @Override
    protected void doUpdate(Resume resume, Integer index) {
        array[index] = resume;
    }

    @Override
    protected void doDelete(Integer index) {
        removeFromArray(index);
        array[--size] = null;
    }

    @Override
    protected Resume doGet(Integer index) {
        return array[index];
    }

    @Override
    protected boolean existsResumeByReference(Integer index) {
        return index >= 0;
    }

    protected abstract Integer findReference(String uuid);

    protected abstract void insertIntoArray(Resume resume, Integer index);

    protected abstract void removeFromArray(Integer index);
}
