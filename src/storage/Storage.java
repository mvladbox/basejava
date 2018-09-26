package storage;

import model.Resume;

/**
 * Storage interface
 */
public interface Storage {

    void clear();

    void save(Resume resume);

    void update(Resume resume);

    void delete(String uuid);

    Resume get(String uuid);

    Resume[] getAll();

    int size();
}
