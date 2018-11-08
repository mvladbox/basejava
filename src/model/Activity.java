package model;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Activity {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/yyyy");

    private Organization organization;
    private YearMonth startDate;
    private YearMonth stopDate;
    private String title;
    private String description;

    public Activity(Organization organization, YearMonth start, YearMonth stop, String title) {
        this(organization, start, stop, title, null);
    }

    public Activity(Organization organization, YearMonth start, YearMonth stop, String title, String description) {
        this.organization = Objects.requireNonNull(organization);
        this.startDate = Objects.requireNonNull(start);
        this.stopDate = stop;
        this.title = Objects.requireNonNull(title);
        this.description = description;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = Objects.requireNonNull(organization);
    }

    public YearMonth getStartDate() {
        return startDate;
    }

    public void setStartDate(YearMonth startDate) {
        this.startDate = Objects.requireNonNull(startDate);
    }

    public YearMonth getStopDate() {
        return (stopDate == null) ? YearMonth.of(2099, 12) : stopDate;
    }

    public void setStopDate(YearMonth stopDate) {
        this.stopDate = stopDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Objects.requireNonNull(title);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return startDate.format(DATE_FORMAT) + " - " +
                ((stopDate != null) ? stopDate.format(DATE_FORMAT) : "Сейчас ") + "  " +
                title + '\n' +
                ((description != null) ? "                     " + description + '\n' : "");
    }
}
