package ru.vlad.app.storage;

import ru.vlad.app.storage.serializer.SerializeByStream;

public class FileStorageTest extends AbstractStorageTest {

    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new SerializeByStream()));
    }
}
