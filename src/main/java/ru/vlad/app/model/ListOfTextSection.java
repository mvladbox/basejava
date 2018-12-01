package ru.vlad.app.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListOfTextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private final List<String> items = new ArrayList<>();

    public ListOfTextSection() {
    }

    public ListOfTextSection(String... items) {
        this(Arrays.asList(items));
    }

    public ListOfTextSection(List<String> items) {
        this.items.addAll(Objects.requireNonNull(items));
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListOfTextSection that = (ListOfTextSection) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (String item : items) {
            str.append("  * ").append(item).append('\n');
        }
        return str.toString();
    }
}
