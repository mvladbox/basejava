package storage;

import exception.ExistsStorageException;
import exception.NotExistsStorageException;
import model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Abstract storage for Resumes
 */
public abstract class AbstractStorage implements Storage {

    public void save(Resume resume) {
        doSave(resume, getNewReference(resume.getUuid()));
    }

    public void update(Resume resume) {
        doUpdate(resume, getReference(resume.getUuid()));
    }

    public void delete(String uuid) {
        doDelete(getReference(uuid));
    }

    public Resume get(String uuid) {
        return doGet(getReference(uuid));
    }

    public List<Resume> getAllSorted() {
        List<Resume> list = Arrays.asList(getAll());
        list.sort((o1, o2) -> {
            int a = o1.getFullName().compareTo(o2.getFullName());
            return (a == 0) ? o1.getUuid().compareTo(o2.getUuid()) : a;
        });
        return list;
    }

    private Object getReference(String uuid) {
        final Object ref = findReference(uuid);
        if (!existsResumeByReference(ref)) {
            throw new NotExistsStorageException(uuid);
        }
        return ref;
    }

    private Object getNewReference(String uuid) {
        final Object ref = findReference(uuid);
        if (existsResumeByReference(ref)) {
            throw new ExistsStorageException(uuid);
        }
        return ref;
    }

    protected abstract void doSave(Resume resume, Object ref);

    protected abstract void doUpdate(Resume resume, Object ref);

    protected abstract void doDelete(Object ref);

    protected abstract Object findReference(String uuid);

    protected abstract boolean existsResumeByReference(Object ref);

    protected abstract Resume doGet(Object ref);

    protected abstract Resume[] getAll();
}
