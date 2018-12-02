package ru.vlad.app.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Contact contact;

    public Organization() {
    }

    public Organization(String name) {
        this.name = Objects.requireNonNull(name);
        this.contact = null;
    }

    public Organization(String name, String url) {
        this.name = Objects.requireNonNull(name);
        if (url != null) {
            this.contact = new Contact(ContactType.HOMEPAGE, name, url);
        }
    }

    public String getName() {
        return name;
    }

    public Contact getContact() {
        return contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(contact, that.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, contact);
    }

    public String toString() {
        return (contact != null) ? contact.toString() : name;
    }
}
