package ru.vlad.app.storage;

import ru.vlad.app.exception.StorageException;
import ru.vlad.app.model.Resume;
import ru.vlad.app.storage.serializer.SerializeStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {

    private Path directory;
    private SerializeStrategy serialization;

    public PathStorage(String dir, SerializeStrategy serialization) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(serialization, "serialization must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.serialization = serialization;
    }

    private Stream<Path> getPathList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Error reading storage directory \"" + directory.toAbsolutePath() + '\"', e);
        }
    }

    @Override
    public void clear() {
        getPathList().forEach(this::doDelete);
    }

    @Override
    protected List<Resume> getAll() {
        return getPathList().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    public int size() {
        return (int) getPathList().count();
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file \"" + path + "\"", path.toString(), e);
        }
        doUpdate(r, path);
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        try {
            serialization.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
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
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return serialization.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Error reading from file \"" + path.toAbsolutePath() + "\"", path.toString(), e);
        }
    }
}
