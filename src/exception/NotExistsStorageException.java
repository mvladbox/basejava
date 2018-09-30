package exception;

public class NotExistsStorageException extends StorageException {

    public NotExistsStorageException(String uuid) {
        super("Резюме " + uuid + " отсутствует", uuid);
    }
}
