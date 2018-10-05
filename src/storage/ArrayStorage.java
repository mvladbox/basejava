package storage;

import model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object findReference(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertIntoArray(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected void slamArray(int index) {
        storage[index] = storage[size - 1];
    }
}
