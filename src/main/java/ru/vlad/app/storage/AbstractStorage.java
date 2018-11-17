package ru.vlad.app.storage;

import ru.vlad.app.exception.ExistStorageException;
import ru.vlad.app.exception.NotExistStorageException;
import ru.vlad.app.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Abstract storage for Resumes
 */
public abstract class AbstractStorage<R> implements Storage {

    private static final Logger log = Logger.getLogger(AbstractStorage.class.getName());

    public void save(Resume resume) {
        log.info("Save " + resume);
        doSave(resume, getReferenceNotExistResume(resume.getUuid()));
    }

    public void update(Resume resume) {
        log.info("Update " + resume);
        doUpdate(resume, getReferenceExistResume(resume.getUuid()));
    }

    public void delete(String uuid) {
        log.info("Delete " + uuid);
        doDelete(getReferenceExistResume(uuid));
    }

    public Resume get(String uuid) {
        log.info("Get " + uuid);
        return doGet(getReferenceExistResume(uuid));
    }

    public List<Resume> getAllSorted() {
        log.info("getAllSorted");
        List<Resume> list = getAll();
        list.sort(Comparator
                    .comparing(Resume::getFullName)
                    .thenComparing(Resume::getUuid));
        return list;
    }

    private R getReferenceExistResume(String uuid) {
        final R ref = detectReference(uuid);
        if (!existResume(ref)) {
            log.warning("Resume " + uuid + " doesn't exist");
            throw new NotExistStorageException(uuid);
        }
        return ref;
    }

    private R getReferenceNotExistResume(String uuid) {
        final R ref = detectReference(uuid);
        if (existResume(ref)) {
            log.warning("Resume " + uuid + " already exists");
            throw new ExistStorageException(uuid);
        }
        return ref;
    }

    protected abstract void doSave(Resume resume, R ref);

    protected abstract void doUpdate(Resume resume, R ref);

    protected abstract void doDelete(R ref);

    protected abstract R detectReference(String uuid);

    protected abstract boolean existResume(R ref);

    protected abstract Resume doGet(R ref);

    protected abstract List<Resume> getAll();
}
