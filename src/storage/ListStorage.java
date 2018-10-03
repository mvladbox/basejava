package storage;

import model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    private final ArrayList<Resume> storage = new ArrayList<Resume>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void save(Resume resume) {
        storage.add(resume);
    }

    @Override
    public void update(Resume resume) {

    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public Resume get(String uuid) {
        return null;//storage.get(storage.indexOf());
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
