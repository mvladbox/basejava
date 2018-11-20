package ru.vlad.app.storage;

import ru.vlad.app.exception.StorageException;
import ru.vlad.app.model.Resume;
import ru.vlad.app.storage.serializer.SerializeStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {

    private File directory;
    private SerializeStrategy serialization;

    public FileStorage(File directory, SerializeStrategy serialization) {
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(serialization, "serialization must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.serialization = serialization;
    }

    @Override
    public void clear() {
        File[] list = directory.listFiles();
        if (list != null) {
            for (File file : list) {
                doDelete(file);
            }
        }
    }

    @Override
    protected List<Resume> getAll() {
        List<Resume> listResume = new ArrayList<>();
        File[] list = directory.listFiles();
        if (list != null) {
            for (File file : list) {
                listResume.add(doGet(file));
            }
        }
        return listResume;
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list == null) {
            throw new StorageException("Read directory error (\"" + directory.getAbsolutePath() + "\")");
        }
        return list.length;
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
            serialization.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Error writing to \"" + file.getAbsolutePath() + "\" file", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            serialization.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Error writing to \"" + file.getAbsolutePath() + "\" file", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File \"" + file.getAbsolutePath() + "\" could not delete", file.getName());
        }
    }

    @Override
    protected File detectReference(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return serialization.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Error reading from file \"" + file.getAbsolutePath() + "\"", file.getName(), e);
        }
    }
}
