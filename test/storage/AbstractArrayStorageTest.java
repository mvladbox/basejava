package storage;

import exception.ExistsStorageException;
import exception.StorageException;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import model.Resume;
import exception.NotExistsStorageException;

import java.lang.reflect.Field;

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
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void save() throws Exception {
        Resume resume = new Resume("trial");
        storage.save(resume);
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test(expected = ExistsStorageException.class)
    public void saveDuplicate() throws Exception {
        storage.save(new Resume(UUID_2));
    }

    @Test
    public void update() throws Exception {
        Resume resumeNew = new Resume(UUID_2);
        assertNotSame(resumeNew, storage.get(UUID_2));
        storage.update(resumeNew);
        assertSame(resumeNew, storage.get(UUID_2));
    }

    @Test(expected = NotExistsStorageException.class)
    public void updateNotExists() throws Exception {
        storage.update(new Resume("dummy"));
    }

    @Test(expected = NotExistsStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_2);
        storage.get(UUID_2);
    }

    @Test
    public void deleteSize() throws Exception {
        storage.delete(UUID_2);
        assertEquals(2, storage.size());
    }

    @Test
    public void get() throws Exception {
        storage.get(UUID_2);
    }

    @Test(expected = NotExistsStorageException.class)
    public void getNotExists() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void size() throws Exception {
        assertEquals(3, storage.size());
    }

    @Test
    public void getAll() throws Exception {
        assertEquals(storage.size(), storage.getAll().length);
    }

    @Test(expected = NotExistsStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = StorageException.class)
    public void overflow() throws Exception {
        storage.clear();
        Field field = AbstractArrayStorage.class.getDeclaredField("RESUME_MAX_COUNT");
        field.setAccessible(true);
        final int LIMIT = field.getInt(storage);

        try {
            for (int i = 0; i < LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (Exception e) {
            fail("Наверно, закончилась память!");
        }

        storage.save(new Resume());
    }
}