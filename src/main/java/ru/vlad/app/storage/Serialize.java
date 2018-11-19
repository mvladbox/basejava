package ru.vlad.app.storage;

import ru.vlad.app.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

interface Serialize {

    void doWrite(Resume r, OutputStream stream) throws IOException;

    Resume doRead(InputStream stream) throws IOException;
}
