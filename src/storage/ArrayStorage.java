package storage;

import model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

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
            storage[index] = storage[size - 1];
            storage[--size] = null;
        } else {
            System.out.println("ОШИБКА: Запрашиваемое резюме отсутствует");
        }
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
