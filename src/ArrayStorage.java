import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void save(Resume resume) {
        storage[size++] = resume;
    }

    Resume get(String uuid) {
        int i = indexOf(uuid);
        return (i >= 0) ? storage[i] : null;
    }

    void delete(String uuid) {
        int i = indexOf(uuid);
        if (i >= 0) {
            storage[i] = storage[size - 1];
            storage[--size] = null;
        }
    }

    private int indexOf(String uuid) {
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
