package ru.vlad.app.model;

import ru.vlad.app.util.YearMonthAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Activity implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/yyyy");

    private Organization organization;
    @XmlJavaTypeAdapter(YearMonthAdapter.class)
    private YearMonth startDate;
    @XmlJavaTypeAdapter(YearMonthAdapter.class)
    private YearMonth endDate;
    private String title;
    private String description;

    public Activity() {
    }

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
