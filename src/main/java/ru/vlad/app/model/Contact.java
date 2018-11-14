package ru.vlad.app.model;

import java.util.Objects;

public class Contact {

    private final ContactType type;
    private final String title;
    private final String value;

    public Contact(ContactType type, String value) {
        this(type, null, value);
    }

    public Contact(ContactType type, String title, String value) {
        this.type = Objects.requireNonNull(type);
        this.title = title;
        this.value = Objects.requireNonNull(value);
    }

    public ContactType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return type == contact.type &&
                Objects.equals(title, contact.title) &&
                Objects.equals(value, contact.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, title, value);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        if (type == ContactType.PHONE || type == ContactType.EMAIL || type == ContactType.SKYPE) {
            str.append(type.getTitle()).append(": ");
        }
        if (type != ContactType.PHONE) {
            str.append('<').append((title == null) ? type.getTitle() : title).append('|');
        }
        str.append(value);
        if (type != ContactType.PHONE) {
            str.append('>');
        }
        return str.toString();
    }
}
