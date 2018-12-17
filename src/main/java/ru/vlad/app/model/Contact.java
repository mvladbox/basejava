package ru.vlad.app.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;

    private ContactType type;
    private String value;

    public Contact() {
    }

    public Contact(ContactType type, String value) {
        this.type = Objects.requireNonNull(type);
        this.value = Objects.requireNonNull(value);
    }

    public ContactType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return type == contact.type &&
                Objects.equals(value, contact.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        if (type == ContactType.PHONE || type == ContactType.EMAIL || type == ContactType.SKYPE) {
            str.append(type.getTitle()).append(": ");
        }
        if (type != ContactType.PHONE && type != ContactType.SKYPE) {
            str.append('<').append(type.getTitle()).append('|');
        }
        str.append(value);
        if (type != ContactType.PHONE && type != ContactType.SKYPE) {
            str.append('>');
        }
        return str.toString();
    }
}
