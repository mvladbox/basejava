package model;

import java.util.Objects;
import java.util.UUID;

/**
 * Resume class
 */
public class Resume {

    // Unique identifier
    private final String uuid;
    private String fullName;

    public Resume(String fullName) {
        this(null, fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = Objects.requireNonNullElse(uuid, UUID.randomUUID().toString());
        this.fullName = Objects.requireNonNull(fullName);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return getUuid() + " - " + getFullName();
    }
}
