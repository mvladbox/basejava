package model;

import java.util.Objects;

public class SimpleTextSection implements Section {

    private String description;

    public SimpleTextSection(String description) {
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
