package storage;

import exception.ExistsStorageException;
import exception.NotExistsStorageException;
import model.Resume;

/**
 * Abstract storage for Resumes
 */
public abstract class AbstractStorage implements Storage {

    public void save(Resume resume) {
        final Object ref = findReference(resume.getUuid());
        if (existsResumeByReference(ref)) {
            throw new ExistsStorageException(resume.getUuid());
        }
        doSave(resume, ref);
    }

    public void update(Resume resume) {
        final Object ref = findReference(resume.getUuid());
        if (!existsResumeByReference(ref)) {
            throw new NotExistsStorageException(resume.getUuid());
        }
        doUpdate(resume, ref);
    }

    public void delete(String uuid) {
        final Object ref = findReference(uuid);
        if (!existsResumeByReference(ref)) {
            throw new NotExistsStorageException(uuid);
        }
        doDelete(ref);
    }

    public Resume get(String uuid) {
        final Object ref = findReference(uuid);
        if (!existsResumeByReference(ref)) {
            throw new NotExistsStorageException(uuid);
        }
        return doGet(ref);
    }

    protected abstract void doSave(Resume resume, Object ref);

    protected abstract void doUpdate(Resume resume, Object ref);

    protected abstract void doDelete(Object ref);

    protected abstract Object findReference(String uuid);

    protected abstract boolean existsResumeByReference(Object ref);

    protected abstract Resume doGet(Object ref);
}
