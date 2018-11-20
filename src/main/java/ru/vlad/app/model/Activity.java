package ru.vlad.app.model;

import java.io.Serializable;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Activity implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/yyyy");

    private final Organization organization;
    private final YearMonth startDate;
    private final YearMonth endDate;
    private final String title;
    private final String description;

    public Activity(Organization organization, YearMonth start, YearMonth end, String title) {
        this(organization, start, end, title, null);
    }

    public Activity(Organization organization, YearMonth start, YearMonth end, String title, String description) {
        this.organization = Objects.requireNonNull(organization);
        this.startDate = Objects.requireNonNull(start);
        this.endDate = end;
        this.title = Objects.requireNonNull(title);
        this.description = description;
    }

    public Organization getOrganization() {
        return organization;
    }

    public YearMonth getStartDate() {
        return startDate;
    }

    public YearMonth getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(organization, activity.organization) &&
                Objects.equals(startDate, activity.startDate) &&
                Objects.equals(endDate, activity.endDate) &&
                Objects.equals(title, activity.title) &&
                Objects.equals(description, activity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organization, startDate, endDate, title, description);
    }

    public String toString() {
        return startDate.format(DATE_FORMAT) + " - " +
                ((endDate != null) ? endDate.format(DATE_FORMAT) : "Сейчас ") + "  " +
                title + '\n' +
                ((description != null) ? "                     " + description + '\n' : "");
    }
}
