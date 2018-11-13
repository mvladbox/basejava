package model;

import java.util.Objects;

public class Organization {

    private final String name;
    private final Contact contact;

    public Organization(String name) {
        this.name = Objects.requireNonNull(name);
        this.contact = null;
    }

    public Organization(String name, String url) {
        this.name = Objects.requireNonNull(name);
        this.contact = new Contact(ContactType.HOMEPAGE, name, url);
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
