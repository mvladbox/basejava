package storage;

import model.Resume;

public class MapResumeStorage extends AbstractMapStorage {

    @Override
    protected void doUpdate(Resume resume, Object key) {
        map.remove((String) key);
        map.put(getKey(resume), resume);
    }

    @Override
    protected String findReference(String uuid) {
        for (Resume resume : map.values()) {
            if (resume.getUuid().equals(uuid)) {
                return getKey(resume);
            }
        }
        return null;
    }

    @Override
    protected String getKey(Resume resume) {
        return resume.toString();
    }
}
