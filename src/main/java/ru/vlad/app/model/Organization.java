package ru.vlad.app.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String url;

    public Organization() {
    }

    public Organization(String name) {
        this.name = Objects.requireNonNull(name);
        this.url = null;
    }

    public Organization(String name, String url) {
        this.name = Objects.requireNonNull(name);
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }

    public String toString() {
        return (url != null) ? '<' + name + '|' + url + '>' : name;
    }
}
