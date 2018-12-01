package ru.vlad.app.storage;

import ru.vlad.app.storage.serializer.SerializeByJson;

public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new SerializeByJson()));
    }
}
