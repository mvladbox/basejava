package storage;

import model.Resume;

/**
 * Abstract array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int RESUME_MAX_COUNT = 10_000;

    protected Resume[] storage = new Resume[RESUME_MAX_COUNT];
    protected int size;

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("ОШИБКА: Резюме с таким UUID отсутствует");
            return null;
        }
        return storage[index];
    }

    public int size() {
        return size;
    }

    protected abstract int getIndex(String uuid);
}
