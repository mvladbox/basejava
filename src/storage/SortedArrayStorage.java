package storage;

import model.Resume;

import java.util.Arrays;

/**
 * Sorted array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object findReference(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid));
    }

    @Override
    protected void insertIntoArray(Resume resume, int index) {
        final int pos = -index - 1;
        System.arraycopy(storage, pos, storage, pos + 1, size - pos);
        storage[pos] = resume;
    }

    @Override
    protected void slamArray(int index) {
        final int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(storage, index + 1, storage, index, numMoved);
        }
    }
}
