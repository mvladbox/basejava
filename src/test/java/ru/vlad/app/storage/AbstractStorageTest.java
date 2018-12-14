package ru.vlad.app.storage;

import ru.vlad.app.Config;
import ru.vlad.app.ResumeTestData;
import ru.vlad.app.exception.ExistStorageException;
import ru.vlad.app.exception.NotExistStorageException;
import ru.vlad.app.model.Contact;
import ru.vlad.app.model.ContactType;
import ru.vlad.app.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.*;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    protected Storage storage;

    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_DUMMY = "dummy";
    private static final String FULL_NAME_1;
    private static final String FULL_NAME_2 = "Fullname Two";
    private static final String FULL_NAME_3 = "First";
    private static final String FULL_NAME_4;
    private static final String FULL_NAME_DUMMY = "Fullname Dummy";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;
    private static final Resume RESUME_DUMMY;

    static {
        FULL_NAME_1 = ResumeTestData.getTestResume().getFullName();
        FULL_NAME_4 = FULL_NAME_1;
        RESUME_1 = ResumeTestData.getTestResume();
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

    @Test(expected = ExistStorageException.class)
    public void saveExists() throws Exception {
        storage.save(RESUME_2);
    }

    @Test
    public void update() throws Exception {
        Resume resumeNew = new Resume(UUID_2, FULL_NAME_DUMMY);
        resumeNew.addContact(new Contact(ContactType.PHONE, "+77777777777"));
        resumeNew.addContact(new Contact(ContactType.EMAIL, "dummy@gmail.com"));
        assertNotSame(resumeNew, storage.get(UUID_2));
        storage.update(resumeNew);
        assertEquals(resumeNew, storage.get(UUID_2));
        resumeNew.addContact(new Contact(ContactType.SKYPE, "dummy.skype"));
        storage.update(resumeNew);
        System.out.println(resumeNew);
        assertEquals(resumeNew, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExists() throws Exception {
        storage.update(RESUME_DUMMY);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_2);
        assertSize(3);
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExists() throws Exception {
        storage.delete(UUID_DUMMY);
    }

    @Test
    public void get() throws Exception {
        assertResumeExists(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExists() throws Exception {
        storage.get(UUID_DUMMY);
    }

    @Test
    public void getAllSorted() throws Exception {
//        List<Resume> listExpected = Arrays.asList(RESUME_3, RESUME_2, RESUME_1, RESUME_4);
        List<Resume> listExpected = new ArrayList<>(Arrays.asList(RESUME_3, RESUME_2, RESUME_1, RESUME_4));
        List<Resume> listActual = storage.getAllSorted();
        assertEquals(storage.size(), listActual.size());
        assertEquals(listExpected, listActual);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertResumeExists(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}
