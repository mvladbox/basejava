package ru.vlad.app.storage.serializer;

import ru.vlad.app.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializeStrategy {

    void doWrite(Resume r, OutputStream stream) throws IOException;

    Resume doRead(InputStream stream) throws IOException;
}
