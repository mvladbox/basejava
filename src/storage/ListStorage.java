package storage;

import model.Resume;

import java.util.List;
import java.util.ArrayList;

/**
 * Collection (ArrayList) based storage for Resumes
 */
public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    public void clear() {
        storage.clear();
    }

    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    public int size() {
        return storage.size();
    }

    @Override
    protected void doSave(Resume resume, Object index) {
        storage.add(resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object index) {
        storage.set((int)index, resume);
    }

    @Override
    protected void doDelete(Object index) {
        storage.remove((int)index);
    }

    @Override
    protected Object findReference(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean existsResumeByReference(Object index) {
        return (int)index >= 0;
    }

    @Override
    protected Resume getResume(Object index) {
        return storage.get((int)index);
    }
}
