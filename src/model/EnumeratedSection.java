package model;

import java.util.ArrayList;
import java.util.List;

public class EnumeratedSection implements Section {

    public final List<String> items = new ArrayList<>();

    public EnumeratedSection(List<String> items) {
        this.items.addAll(items);
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
