package ru.vlad.app.model;

import java.util.Objects;

public class SimpleTextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private String description;

    public SimpleTextSection() {
    }

    public SimpleTextSection(String description) {
        this.description = Objects.requireNonNull(description);
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleTextSection that = (SimpleTextSection) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public String toString() {
        return "  " + description + '\n';
    }
}
