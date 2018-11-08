package model;

import java.util.*;

public class ActivitySection implements Section {

    private static final Comparator<Activity> DESCENT = Comparator.comparing(Activity::getStopDate).reversed();

    public final Set<Activity> items = new TreeSet<>(DESCENT);

    public ActivitySection(List<Activity> items) {
        this.items.addAll(items);
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
