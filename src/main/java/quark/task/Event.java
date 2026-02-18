package quark.task;

import quark.save.SaveManager;

public class Event extends Task {
    public static final String PREFIX = "E";
    public static final String EVENT_FORMAT = "%1$s (from: %2$s, to: %3$s)";

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
        return String.format(EVENT_FORMAT, getMetaDisplayString(), startDate, endDate);
    }

    @Override
    public String toSaveString() {
        return getMetaSaveString() + SaveManager.SAVE_DELIMITER
                + startDate + SaveManager.SAVE_DELIMITER + endDate;
    }
}
