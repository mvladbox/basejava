package storage;

import exception.StorageException;

import static org.junit.Assert.*;

import org.junit.Test;
import model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(AbstractArrayStorage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.RESUME_MAX_COUNT; i++) {
                storage.save(new Resume("Trial" + i));
            }
        } catch (StorageException e) {
            fail("Наверно, закончилась память!");
        }
        storage.save(new Resume("Overflow"));
    }

}