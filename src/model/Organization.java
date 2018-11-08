package model;

import java.util.Objects;

public class Organization {

    private String name;
    private Link link;

    public Organization(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public Organization(String name, String url) {
        this.name = Objects.requireNonNull(name);
        this.link = new Link(name, url);
    }

    public String toString() {
        return (link != null) ? link.toString() : name;
    }
}
