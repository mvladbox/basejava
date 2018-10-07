package storage;

import exception.ExistsStorageException;
import exception.NotExistsStorageException;
import model.Resume;

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
}
