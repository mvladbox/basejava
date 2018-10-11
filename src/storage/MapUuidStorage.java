package storage;

import model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
    private final Map<String, Resume> map = new HashMap<>();

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
    protected void doSave(Resume resume, String uuid) {
        map.put(uuid, resume);
    }

    @Override
    protected void doUpdate(Resume resume, String uuid) {
        map.replace(uuid, resume);
    }

    @Override
    protected void doDelete(String uuid) {
        map.remove(uuid);
    }

    @Override
    protected Resume doGet(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected String findReference(String uuid) {
        return uuid;
    }

    @Override
    protected boolean existsResumeByReference(String uuid) {
        return map.containsKey(uuid);
    }
}
