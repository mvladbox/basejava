package ru.vlad.app.storage;

import org.junit.Test;
import ru.vlad.app.exception.StorageException;
import ru.vlad.app.model.Resume;

import static org.junit.Assert.fail;

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
            fail("Exception raised before inserting overflow element");
        }
        storage.save(new Resume("Overflow"));
    }

}