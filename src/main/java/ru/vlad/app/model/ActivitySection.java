package ru.vlad.app.model;

//import java.time.YearMonth;
import java.util.*;

public class ActivitySection extends Section {
    private static final long serialVersionUID = 1L;

//    private static final Comparator<Activity> DESCENT = Comparator.comparing((Activity activity) ->
//            (activity.getEndDate() == null) ? YearMonth.of(2099, 12) : activity.getEndDate())
//            .reversed();

    private final Set<Activity> items = new HashSet<>();  //TreeSet<>(/ESCENT);

    public ActivitySection(Activity... items) {
        this(Arrays.asList(Objects.requireNonNull(items)));
    }

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
