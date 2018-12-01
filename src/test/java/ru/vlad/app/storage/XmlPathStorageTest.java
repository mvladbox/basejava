package ru.vlad.app.storage;

import ru.vlad.app.storage.serializer.SerializeByXml;

public class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new SerializeByXml()));
    }
}
