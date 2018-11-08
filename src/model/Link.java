package model;

import java.util.Objects;

public class Link {
    private String title;
    private String url;

    public Link(String title, String url) {
        this.title = title;
        this.url = Objects.requireNonNull(url);
    }

    public Link(String url) {
        this(null, url);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString() {
        return '<' + ((title != null) ? title + '|' : "") + url + '>';
    }
}
