package storage;

import exception.ExistsStorageException;
import exception.NotExistsStorageException;
import model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Abstract storage for Resumes
 */
public abstract class AbstractStorage<R> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public void save(Resume resume) {
        LOG.info("Save " + resume);
        doSave(resume, getNewReference(resume.getUuid()));
    }

    public void update(Resume resume) {
        LOG.info("Update " + resume);
        doUpdate(resume, getReference(resume.getUuid()));
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        doDelete(getReference(uuid));
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return doGet(getReference(uuid));
    }

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = getAll();
        list.sort(Comparator
                    .comparing(Resume::getFullName)
                    .thenComparing(Resume::getUuid));
        return list;
    }

    private R getReference(String uuid) {
        final R ref = findReference(uuid);
        if (!existsResumeByReference(ref)) {
            LOG.warning("Resume " + uuid + " not exists");
            throw new NotExistsStorageException(uuid);
        }
        return ref;
    }

    private R getNewReference(String uuid) {
        final R ref = findReference(uuid);
        if (existsResumeByReference(ref)) {
            LOG.warning("Resume " + uuid + " already exists");
            throw new ExistsStorageException(uuid);
        }
        return ref;
    }

    protected abstract void doSave(Resume resume, R ref);

    protected abstract void doUpdate(Resume resume, R ref);

    protected abstract void doDelete(R ref);

    protected abstract R findReference(String uuid);

    protected abstract boolean existsResumeByReference(R ref);

    protected abstract Resume doGet(R ref);

    protected abstract List<Resume> getAll();
}
