package model;

import java.time.YearMonth;
import java.util.*;

public class ActivitySection implements Section {

    private static final Comparator<Activity> DESCENT = Comparator.comparing((Activity activity) ->
            (activity.getEndDate() == null) ? YearMonth.of(2099, 12) : activity.getEndDate())
            .reversed();

    private final Set<Activity> items = new TreeSet<>(DESCENT);

    public ActivitySection(List<Activity> items) {
        this.items.addAll(Objects.requireNonNull(items));
    }

    public List<Activity> getItems() {
        return new ArrayList<>(items);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivitySection that = (ActivitySection) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        Organization org = null;
        for (Activity item : items) {
            if (item.getOrganization() != org) {
                org = item.getOrganization();
                str.append("  ").append(org).append('\n');
            }
            str.append("  ").append(item);
        }
        return str.toString();
    }
}
