package ru.vlad.app.storage;

import org.junit.Before;
import org.junit.Test;
import ru.vlad.app.Config;
import ru.vlad.app.exception.ExistStorageException;
import ru.vlad.app.exception.NotExistStorageException;
import ru.vlad.app.model.Contact;
import ru.vlad.app.model.ContactType;
import ru.vlad.app.model.Resume;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static ru.vlad.app.ResumeTestData.*;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    protected Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
        storage.save(R4);
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
        storage.save(R_DUMMY);
        assertSize(5);
        assertResumeExists(R_DUMMY);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExists() throws Exception {
        storage.save(R2);
    }

    @Test
    public void update() throws Exception {
        Resume resumeNew = new Resume(UUID_2, FULL_NAME_DUMMY);
        resumeNew.addContact(new Contact(ContactType.PHONE, "+77777777777"));
        resumeNew.addContact(new Contact(ContactType.EMAIL, "dummy@gmail.com"));
        assertNotSame(resumeNew, storage.get(UUID_2));
        storage.update(resumeNew);
        assertEquals(resumeNew, storage.get(UUID_2));
        resumeNew.addContact(new Contact(ContactType.PHONE, "+78888888888"));
        resumeNew.addContact(new Contact(ContactType.SKYPE, "dummy.skype"));
        storage.update(resumeNew);
        System.out.println(resumeNew);
        assertEquals(resumeNew, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExists() throws Exception {
        storage.update(R_DUMMY);
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
        assertResumeExists(R1);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExists() throws Exception {
        storage.get(UUID_DUMMY);
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> listExpected = Arrays.asList(R3, R2, R1, R4);
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
