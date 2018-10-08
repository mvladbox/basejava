package storage;

import model.Resume;

import java.util.List;
import java.util.ArrayList;

/**
 * Collection (ArrayList) based storage for Resumes
 */
public class ListStorage extends AbstractStorage {
    private final List<Resume> list = new ArrayList<>();

    public void clear() {
        list.clear();
    }

    public Resume[] getAll() {
        return list.toArray(new Resume[0]);
    }

    public int size() {
        return list.size();
    }

    @Override
    protected void doSave(Resume resume, Object index) {
        list.add(resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object index) {
        list.set((Integer) index, resume);
    }

    @Override
    protected void doDelete(Object index) {
        list.remove(((Integer) index).intValue());
    }

    @Override
    protected Integer findReference(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean existsResumeByReference(Object index) {
        return (Integer) index >= 0;
    }

    @Override
    protected Resume doGet(Object index) {
        return list.get((Integer) index);
    }
}
