package storage;

import model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage implements Storage{
    private static final int RESUME_MAX_COUNT = 10_000;
    private Resume[] storage = new Resume[RESUME_MAX_COUNT];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size == RESUME_MAX_COUNT) {
            System.out.println("ОШИБКА: Достигнут предел количества сохраняемых резюме (" +
                    RESUME_MAX_COUNT + ")");
        } else if (getIndex(resume.getUuid()) >= 0) {
            System.out.println("ОШИБКА: Резюме с таким UUID уже сохранено");
        } else {
            storage[size++] = resume;
        }
    }

    public void update(Resume resume) {
        int i = getIndex(resume.getUuid());
        if (i >= 0) {
            storage[i] = resume;
        } else {
            System.out.println("ОШИБКА: Резюме с таким UUID отсутствует");
        }
    }

    public void delete(String uuid) {
        int i = getIndex(uuid);
        if (i >= 0) {
            storage[i] = storage[size - 1];
            storage[--size] = null;
        } else {
            System.out.println("ОШИБКА: Запрашиваемое резюме отсутствует");
        }
    }

    public Resume get(String uuid) {
        int i = getIndex(uuid);
        if (i < 0) {
            System.out.println("ОШИБКА: Резюме с таким UUID отсутствует");
            return null;
        }
        return storage[i];
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
