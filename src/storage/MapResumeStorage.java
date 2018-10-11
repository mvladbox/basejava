package storage;

import model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
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
    protected void doSave(Resume newResume, Resume resume) {
        map.put(newResume.getUuid(), newResume);
    }

    @Override
    protected void doUpdate(Resume newResume, Resume resume) {
        map.replace((resume).getUuid(), newResume);
    }

    @Override
    protected void doDelete(Resume resume) {
        map.remove((resume).getUuid());
    }

    @Override
    protected Resume doGet(Resume resume) {
        return resume;
    }

    @Override
    protected Resume findReference(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean existsResumeByReference(Resume resume) {
        return resume != null;
    }
}
