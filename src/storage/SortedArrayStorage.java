package storage;

import model.Resume;

import java.util.Arrays;

/**
 * Sorted array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    protected void doSave(Resume resume, int index) {
        int pos = -index - 1;
        System.arraycopy(storage, pos, storage, pos + 1, size - pos);
        storage[pos] = resume;
    }

    protected void doDelete(int index) {
        if (index < size - 1) {
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        }
    }
}
