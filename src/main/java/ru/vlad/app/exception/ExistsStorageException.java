package ru.vlad.app.exception;

public class ExistsStorageException extends StorageException{

    public ExistsStorageException(String uuid) {
        super("Резюме " + uuid + " уже существует", uuid);
    }
}
