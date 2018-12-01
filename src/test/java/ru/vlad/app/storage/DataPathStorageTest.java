package ru.vlad.app.storage;

import ru.vlad.app.storage.serializer.SerializeByDataStream;

public class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new SerializeByDataStream()));
    }
}
