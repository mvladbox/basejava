package storage;

import exception.ExistsStorageException;
import exception.NotExistsStorageException;
import model.Resume;

/**
 * Abstract storage for Resumes
 */
public abstract class AbstractStorage implements Storage {

    public void save(Resume resume) {
        final int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistsStorageException(resume.getUuid());
        }
        doSave(resume, index);
    }

    public void update(Resume resume) {
        final int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistsStorageException(resume.getUuid());
        }
        doUpdate(resume, index);
    }

    public void delete(String uuid) {
        final int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistsStorageException(uuid);
        }
        doDelete(index);
    }

    public Resume get(String uuid) {
        final int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistsStorageException(uuid);
        }
        return getResume(index);
    }

    protected abstract void doSave(Resume resume, int index);

    protected abstract void doUpdate(Resume resume, int index);

    protected abstract void doDelete(int index);

    protected abstract int getIndex(String uuid);

    protected abstract Resume getResume(int index);
}
