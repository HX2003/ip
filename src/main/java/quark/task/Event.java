package quark.task;

import java.util.ArrayList;

public class Event extends Task {
    public static final String PREFIX = "E";
    public static final String FORMAT_EVENT = "%1$s (from: %2$s, to: %3$s)";

    private final String startDate;
    private final String endDate;

    public Event(String description, String startDate, String endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    @Override
    public String toString() {
        return String.format(FORMAT_EVENT, getMetaDisplayString(), startDate, endDate);
    }

    @Override
    public ArrayList<String> toSaveStrings() {
        ArrayList<String> strings = getMetaSaveStrings();
        strings.add(startDate);
        strings.add(endDate);
        return strings;
    }
}
