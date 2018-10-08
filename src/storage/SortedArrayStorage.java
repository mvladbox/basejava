package storage;

import model.Resume;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Sorted array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    @Override
    protected Integer findReference(String uuid) {
        return Arrays.binarySearch(array, 0, size, new Resume(uuid), RESUME_COMPARATOR);
    }

    @Override
    protected void insertIntoArray(Resume resume, Integer index) {
        final int pos = -index - 1;
        System.arraycopy(array, pos, array, pos + 1, size - pos);
        array[pos] = resume;
    }

    @Override
    protected void removeFromArray(Integer index) {
        final int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(array, index + 1, array, index, numMoved);
        }
    }
}
