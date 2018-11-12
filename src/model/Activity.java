package model;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Activity {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/yyyy");

    private Organization organization;
    private YearMonth startDate;
    private YearMonth endDate;
    private String title;
    private String description;

    public Activity(Organization organization, YearMonth start, YearMonth stop, String title) {
        this(organization, start, stop, title, null);
    }

    public Activity(Organization organization, YearMonth start, YearMonth stop, String title, String description) {
        this.organization = Objects.requireNonNull(organization);
        this.startDate = Objects.requireNonNull(start);
        this.endDate = stop;
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

    public YearMonth getEndDate() {
        return endDate;
    }

    public void setEndDate(YearMonth endDate) {
        this.endDate = endDate;
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
