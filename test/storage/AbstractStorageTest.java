package storage;

import exception.ExistsStorageException;
import exception.NotExistsStorageException;
import model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_DUMMY = "dummy";
    private static final String FULL_NAME_1 = "Fullname One";
    private static final String FULL_NAME_2 = "Fullname Two";
    private static final String FULL_NAME_3 = "First";
    private static final String FULL_NAME_4 = FULL_NAME_1;
    private static final String FULL_NAME_DUMMY = "Fullname Dummy";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;
    private static final Resume RESUME_DUMMY;

    static {
        RESUME_1 = new Resume(UUID_1, FULL_NAME_1);
        RESUME_2 = new Resume(UUID_2, FULL_NAME_2);
        RESUME_3 = new Resume(UUID_3, FULL_NAME_3);
        RESUME_4 = new Resume(UUID_4, FULL_NAME_4);
        RESUME_DUMMY = new Resume(UUID_DUMMY, FULL_NAME_DUMMY);
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
        storage.save(RESUME_4);
    }

    @Test
    public void size() throws Exception {
        assertSize(4);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_DUMMY);
        assertSize(5);
        assertResumeExists(RESUME_DUMMY);
    }

    @Test(expected = ExistsStorageException.class)
    public void saveExists() throws Exception {
        storage.save(RESUME_2);
    }

    @Test
    public void update() throws Exception {
        Resume resumeNew = new Resume(UUID_2, FULL_NAME_DUMMY);
        assertNotSame(resumeNew, storage.get(UUID_2));
        storage.update(resumeNew);
        assertSame(resumeNew, storage.get(UUID_2));
    }

    @Test(expected = NotExistsStorageException.class)
    public void updateNotExists() throws Exception {
        storage.update(RESUME_DUMMY);
    }

    @Test(expected = NotExistsStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_2);
        assertSize(3);
        storage.get(UUID_2);
    }

    @Test(expected = NotExistsStorageException.class)
    public void deleteNotExists() throws Exception {
        storage.delete(UUID_DUMMY);
    }

    @Test
    public void get() throws Exception {
        assertResumeExists(RESUME_2);
    }

    @Test(expected = NotExistsStorageException.class)
    public void getNotExists() throws Exception {
        storage.get(UUID_DUMMY);
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> list = storage.getAllSorted();
        assertEquals(4, list.size());
        assertEquals(list, Arrays.asList(RESUME_3, RESUME_1, RESUME_4, RESUME_2));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertResumeExists(Resume resume) {
        assertSame(resume, storage.get(resume.getUuid()));
    }
}
