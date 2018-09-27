package storage;

import model.Resume;

import java.util.Arrays;

/**
 * Abstract array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int RESUME_MAX_COUNT = 10_000;

    protected Resume[] storage = new Resume[RESUME_MAX_COUNT];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size == RESUME_MAX_COUNT) {
            System.out.println("ОШИБКА: Достигнут предел количества сохраняемых резюме (" + RESUME_MAX_COUNT + ")");
        } else {
            int index = getIndex(resume.getUuid());
            if (index >= 0) {
                System.out.println("ОШИБКА: Резюме с таким UUID уже сохранено");
            } else {
                doSave(resume, index);
                size++;
            }
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.println("ОШИБКА: Резюме с таким UUID отсутствует");
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            doDelete(index);
            storage[--size] = null;
        } else {
            System.out.println("ОШИБКА: Запрашиваемое резюме отсутствует");
        }
    }

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

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void doSave(Resume resume, int index);

    protected abstract void doDelete(int index);
}
