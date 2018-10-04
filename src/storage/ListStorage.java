package storage;

import model.Resume;

import java.util.ArrayList;

/**
 * Collection (ArrayList) based storage for Resumes
 */
public class ListStorage extends AbstractStorage {
    private final ArrayList<Resume> storage = new ArrayList<Resume>();

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
    protected void doSave(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected void doUpdate(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    protected void doDelete(int index) {
        storage.remove(index);
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (getResume(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Resume getResume(int index) {
        return storage.get(index);
    }
}
