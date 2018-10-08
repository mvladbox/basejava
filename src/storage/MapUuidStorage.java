package storage;

import model.Resume;

public class MapUuidStorage extends AbstractMapStorage {

    @Override
    protected String findReference(String uuid) {
        return uuid;
    }

    @Override
    protected String getKey(Resume resume) {
        return resume.getUuid();
    }
}
