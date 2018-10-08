package storage;

import model.Resume;

import java.util.List;

/**
 * Storage interface
 */
public interface Storage {

    void clear();

    void save(Resume resume);

    void update(Resume resume);

    void delete(String uuid);

    Resume get(String uuid);

    List<Resume> getAllSorted();

    int size();
}
