package ru.vlad.app.storage;

public class ObjectStreamStorageTest extends AbstractStorageTest {

    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }
}
