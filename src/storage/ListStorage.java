package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.List;

/**
 * Collection (ArrayList) based storage for Resumes
 */
public class ListStorage extends AbstractStorage<Integer> {
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
    protected void doSave(Resume resume, Integer index) {
        list.add(resume);
    }

    @Override
    protected void doUpdate(Resume resume, Integer index) {
        list.set(index, resume);
    }

    @Override
    protected void doDelete(Integer index) {
        list.remove(index.intValue());
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
    protected boolean existsResumeByReference(Integer index) {
        return index >= 0;
    }

    @Override
    protected Resume doGet(Integer index) {
        return list.get(index);
    }
}
