package model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Resume class
 */
public class Resume {

    // Unique identifier
    private final String uuid;
    private final String fullName;

    private final Map<ContactType, Contact> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = Objects.requireNonNull(uuid, "uuid must not be null");
        this.fullName = Objects.requireNonNull(fullName, "fullName must not be null");
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Contact getContact(ContactType type) {
        return contacts.get(type);
    }

    public Section getSection(SectionType type) {
        return sections.get(type);
    }

    public void addContact(Contact contact) {
        contacts.put(Objects.requireNonNull(contact).getType(), contact);
    }

    public void addSection(SectionType type, Section section) {
        sections.put(Objects.requireNonNull(type), Objects.requireNonNull(section));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(fullName + '\n');

        for (Contact contact : contacts.values()) {
            str.append(contact).append('\n');
        }
        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            str.append("\n").append(entry.getKey().getTitle()).append('\n');
            str.append(entry.getValue());
        }
        return str.toString();
    }
}
