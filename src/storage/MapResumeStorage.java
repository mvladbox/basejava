package storage;

import model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    protected final Map<Integer, Resume> map = new HashMap<>();

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
        map.put(resume.hashCode(), resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object key) {
        map.replace((Integer) key, resume);
    }

    @Override
    protected void doDelete(Object key) {
        map.remove((Integer) key);
    }

    @Override
    protected Resume doGet(Object key) {
        return map.get((Integer) key);
    }

    @Override
    protected Integer findReference(String uuid) {
        for (Resume resume : map.values()) {
            if (resume.getUuid().equals(uuid)) {
                return resume.hashCode();
            }
        }
        return -1;
    }

    @Override
    protected boolean existsResumeByReference(Object key) {
        return map.containsKey((Integer) key);
    }
}
