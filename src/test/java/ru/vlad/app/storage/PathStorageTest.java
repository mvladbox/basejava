package ru.vlad.app.storage;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new StandardSerialization()));
    }
}
