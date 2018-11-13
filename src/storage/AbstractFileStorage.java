package storage;

import exception.StorageException;
import model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
    }

    @Override
    protected List<Resume> getAll() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(Resume r, File file) {
    }

    @Override
    protected void doDelete(File file) {
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;

    @Override
    protected File findReference(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean existsResumeByReference(File file) {
        return file.exists();
    }

    @Override
    protected Resume doGet(File file) {
        return null;
    }
}
