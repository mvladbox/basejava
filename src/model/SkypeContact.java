package model;

public class SkypeContact extends LinkedContact {

    public SkypeContact(String skype) {
        super(ContactType.CT_SKYPE.getTitle(), skype, "skype:" + skype);
    }
}
