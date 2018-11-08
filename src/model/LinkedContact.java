package model;

public abstract class LinkedContact extends Contact {

    private Link link;

    public LinkedContact(String label, String title, String url) {
        super(label, null);
        this.link = new Link(title, url);
    }

    @Override
    public String getValue() {
        return link.toString();
    }

    @Override
    public void setValue(String url) {
        link.setUrl(url);
    }
}
