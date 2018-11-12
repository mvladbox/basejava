package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListOfTextSection implements Section {

    private final List<String> items = new ArrayList<>();

    public ListOfTextSection(List<String> items) {
        this.items.addAll(items);
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
