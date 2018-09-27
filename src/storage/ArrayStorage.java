package storage;

import model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    protected void doSave(Resume resume, int index) {
        storage[size] = resume;
    }

    protected void doUpdate(int index, Resume resume) {
        storage[index] = resume;
    }

    protected void doDelete(int index) {
        storage[index] = storage[size - 1];
    }
}
