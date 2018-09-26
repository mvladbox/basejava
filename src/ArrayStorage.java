import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int RESUME_MAX_COUNT = 10_000;
    private Resume[] storage = new Resume[RESUME_MAX_COUNT];
    private int size;

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void save(Resume resume) {
        if (size == RESUME_MAX_COUNT) {
            System.out.println("ОШИБКА: Достигнут предел количества сохраняемых резюме (" +
                    RESUME_MAX_COUNT + ")");
        } else  if (getIndex(resume.uuid) >= 0) {
            System.out.println("ОШИБКА: Резюме с таким UUID уже сохранено");
        } else {
            storage[size++] = resume;
        }
    }

    void update(Resume resume) {
        int i = getIndex(resume.uuid);
        if (i >= 0) {
            storage[i] = resume;
        } else {
            System.out.println("ОШИБКА: Резюме с таким UUID отсутствует");
        }
    }

    Resume get(String uuid) {
        int i = getIndex(uuid);
        return (i >= 0) ? storage[i] : null;
    }

    void delete(String uuid) {
        int i = getIndex(uuid);
        if (i >= 0) {
            storage[i] = storage[size - 1];
            storage[--size] = null;
        } else {
            System.out.println("ОШИБКА: Запрашиваемое резюме отсутствует");
        }
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    int size() {
        return size;
    }
}
