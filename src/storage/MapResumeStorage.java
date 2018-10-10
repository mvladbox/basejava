package storage;

import model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
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
    protected void doSave(Resume newResume, Object resume) {
        map.put(newResume.getUuid(), newResume);
    }

    @Override
    protected void doUpdate(Resume newResume, Object resume) {
        map.replace(((Resume) resume).getUuid(), newResume);
    }

    @Override
    protected void doDelete(Object resume) {
        map.remove(((Resume) resume).getUuid());
    }

    @Override
    protected Resume doGet(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected Resume findReference(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean existsResumeByReference(Object resume) {
        return resume != null;
    }
}
