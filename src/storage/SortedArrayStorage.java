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

    protected void doSave(Resume resume) {
        int pos = getNewPosition(resume);

        // Раздвинуть массив и заполнить ячейку новым элементом
        System.arraycopy(storage, pos, storage, pos + 1, size - pos);
        storage[pos] = resume;
    }

    protected void doUpdate(int index, Resume resume) {
        delete(storage[index].getUuid());
        save(resume);
    }

    protected void doDelete(int index) {
        // Сместить массив на удаляемый элемент
        System.arraycopy(storage, index + 1, storage, index, size - index);
    }

    private int getNewPosition(Resume resume) {
        int left = 0;
        int right = (size > 0) ? size - 1 : 0;

        while (left < right) {
            int middle = (left + right) >>> 1;
            if (resume.compareTo(storage[middle]) < 0)
                right = middle;
            else
                left = middle + 1;
        }
        if (left == size - 1 && resume.compareTo(storage[left]) > 0)
            left = size;

        return left;
    }
}
