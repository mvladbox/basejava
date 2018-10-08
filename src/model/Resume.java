package model;

import java.util.UUID;

/**
 * Resume class
 */
public class Resume {

    // Unique identifier
    private final String uuid;
    private String fullName;

    private static String normalizeString(String s) {
        return (s == null) ? "" : s;
    }

    public Resume() {
        this(UUID.randomUUID().toString(), null);
    }

    public Resume(String uuid) {
        this(uuid, null);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = normalizeString(fullName);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    protected void setFullName(String fullName) {
        this.fullName = normalizeString(fullName);
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
