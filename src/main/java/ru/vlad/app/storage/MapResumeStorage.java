package ru.vlad.app.storage;

import ru.vlad.app.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private final Map<String, Resume> map = new HashMap<>();

    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(map.values());
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
        map.replace(resume.getUuid(), newResume);
    }

    @Override
    protected void doDelete(Resume resume) {
        map.remove(resume.getUuid());
    }

    @Override
    protected Resume doGet(Resume resume) {
        return resume;
    }

    @Override
    protected Resume detectReference(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean existResume(Resume resume) {
        return resume != null;
    }
}
