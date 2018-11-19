package ru.vlad.app.storage;

import ru.vlad.app.exception.StorageException;
import ru.vlad.app.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {

    private Path directory;
    private Serialize serialization;

    public PathStorage(String dir, Serialize serialization) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(serialization, "serialization must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.serialization = serialization;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Error reading storage directory \"" + directory.toAbsolutePath() + '\"', null, e);
        }
    }

    @Override
    protected List<Resume> getAll() {
        try {
            return Files.list(directory).map(this::doGet).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Error reading storage directory \"" + directory.toAbsolutePath() + '\"', null, e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Error reading storage directory \"" + directory.toAbsolutePath() + '\"', null, e);
        }
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
            serialization.doWrite(r, new BufferedOutputStream(new FileOutputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("Error writing to \"" + path + "\" file", path.toString(), e);
        }
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        try {
            serialization.doWrite(r, new BufferedOutputStream(new FileOutputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("Error writing to \"" + path + "\" file", path.toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("File \"" + path + "\" could not delete", path.toString());
        }
    }

    @Override
    protected Path detectReference(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return serialization.doRead(new BufferedInputStream(new FileInputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("Error reading from file \"" + path.toAbsolutePath() + "\"", path.toString(), e);
        }
    }
}
