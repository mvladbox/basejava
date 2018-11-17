package ru.vlad.app.storage;

import ru.vlad.app.exception.StorageException;
import ru.vlad.app.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {

    private Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }
/*
    @Override
    protected List<Resume> getAll() {
        List<Resume> listResume = new ArrayList<>();
        Path[] list = directory.listPaths();
        if (list != null) {
            for (Path Path : list) {
                listResume.add(doGet(Path));
            }
        }
        return listResume;
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list == null) {
            throw new StorageException("Read directory error (\"" + directory.getAbsolutePath() + "\")", null);
        }
        return list.length;
    }

    @Override
    protected void doSave(Resume r, Path Path) {
        try {
            Path.createNewPath();
            doWrite(r, new BufferedOutputStream(new PathOutputStream(Path)));
        } catch (IOException e) {
            throw new StorageException("Error writing to \"" + Path.getAbsolutePath() + "\" Path", Path.getName(), e);
        }
    }

    @Override
    protected void doUpdate(Resume r, Path Path) {
        try {
            doWrite(r, new BufferedOutputStream(new PathOutputStream(Path)));
        } catch (IOException e) {
            throw new StorageException("Error writing to \"" + Path.getAbsolutePath() + "\" Path", Path.getName(), e);
        }
    }

    @Override
    protected void doDelete(Path Path) {
        if (!Path.delete()) {
            throw new StorageException("Path \"" + Path.getAbsolutePath() + "\" could not delete", Path.getName());
        }
    }

    @Override
    protected Path detectReference(String uuid) {
        return new Path(directory, uuid);
    }

    @Override
    protected boolean isExist(Path Path) {
        return Path.exists();
    }

    @Override
    protected Resume doGet(Path Path) {
        try {
            return doRead(new BufferedInputStream(new PathInputStream(Path)));
        } catch (IOException e) {
            throw new StorageException("Error reading from Path \"" + Path.getAbsolutePath() + "\"", Path.getName(), e);
        }
    }
*/
    protected abstract void doWrite(Resume r, OutputStream stream) throws IOException;

    protected abstract Resume doRead(InputStream stream) throws IOException;
}
