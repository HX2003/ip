package quark.task;

import quark.save.SaveManager;

public class Event extends Task {
    public static final String PREFIX = "E";

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
        return getMetaDisplayString() + " (from: " + startDate + ", to: " + endDate + ")";
    }

    @Override
    public String toSaveString() {
        return getMetaSaveString() + SaveManager.SAVE_DELIMITER
                + startDate + SaveManager.SAVE_DELIMITER + endDate;
    }
}
