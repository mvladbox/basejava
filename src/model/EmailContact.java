package model;

public class EmailContact extends LinkedContact {

    public EmailContact(String email) {
        super(ContactType.CT_EMAIL.getTitle(), email, "mailto:" + email);
    }
}
