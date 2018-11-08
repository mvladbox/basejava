package model;

public enum ContactType {
    CT_PHONE("Тел."),
    CT_SKYPE("Skype"),
    CT_EMAIL("Почта"),
    CT_LINKEDIN("Профиль LinkedIn"),
    CT_GITHUB("Профиль GitHub"),
    CT_STACKOVERFLOW("Профиль Stackoverflow"),
    CT_HOMEPAGE("Домашняя страница");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
