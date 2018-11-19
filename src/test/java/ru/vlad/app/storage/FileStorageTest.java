package ru.vlad.app.storage;

public class FileStorageTest extends AbstractStorageTest {

    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new StandardSerialization()));
    }
}
