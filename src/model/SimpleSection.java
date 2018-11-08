package model;

import java.util.Objects;

public class SimpleSection implements Section {

    private String description;

    public SimpleSection() {
    }

    SimpleSection(String description) {
        this.description = Objects.requireNonNull(description);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = Objects.requireNonNull(description);
    }

    @Override
    public String toString() {
        return (description != null) ? "  " + description + '\n' : "";
    }
}
