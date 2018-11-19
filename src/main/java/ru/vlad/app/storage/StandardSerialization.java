package ru.vlad.app.storage;

import ru.vlad.app.exception.StorageException;
import ru.vlad.app.model.Resume;

import java.io.*;

public class StandardSerialization implements Serialize {

    public void doWrite(Resume r, OutputStream stream) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(stream)) {
            oos.writeObject(r);
        }
    }

    public Resume doRead(InputStream stream) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(stream)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
