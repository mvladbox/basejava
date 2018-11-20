package ru.vlad.app.storage;

import ru.vlad.app.storage.serializer.SerializeByStream;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new SerializeByStream()));
    }
}
