package storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import model.Resume;
import exception.NotExistsStorageException;

public abstract class AbstractArrayStorageTest {

    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_3));
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
    }

    @Test
    public void clear() throws Exception {
    }

    @Test
    public void save() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void get() throws Exception {
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void getAll() throws Exception {
    }

    @Test(expected = NotExistsStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}