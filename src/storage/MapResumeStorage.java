package storage;

import model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    protected final Map<Resume, Resume> map = new HashMap<>();

    public void clear() {
        map.clear();
    }

    @Override
    public Resume[] getAll() {
        return map.values().toArray(new Resume[0]);
    }

    public int size() {
        return map.size();
    }

    @Override
    protected void doSave(Resume resume, Object key) {
        map.put(resume, resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object key) {
        map.replace((Resume) key, resume);
    }

    @Override
    protected void doDelete(Object key) {
        map.remove((Resume) key);
    }

    @Override
    protected Resume doGet(Object key) {
        return map.get((Resume) key);
    }

    @Override
    protected Resume findReference(String uuid) {
        for (Resume resume : map.values()) {
            if (resume.getUuid().equals(uuid)) {
                return resume;
            }
        }
        return null;
    }

    @Override
    protected boolean existsResumeByReference(Object key) {
        return key != null;
    }
}
