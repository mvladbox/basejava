package ru.vlad.app.storage;

import ru.vlad.app.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer findReference(String uuid) {
        for (int i = 0; i < size; i++) {
            if (array[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertIntoArray(Resume resume, Integer index) {
        array[size] = resume;
    }

    @Override
    protected void removeFromArray(Integer index) {
        array[index] = array[size - 1];
    }
}
