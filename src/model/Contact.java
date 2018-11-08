package model;

import java.util.Objects;

public class Contact {

    private String label;
    private String value;

    public Contact(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String title) {
        this.label = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(label, contact.getLabel()) &&
                Objects.equals(getValue(), contact.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, value);
    }

    @Override
    public String toString() {
        return ((label != null) ? label + ": " : "") + getValue() + '\n';
    }
}
