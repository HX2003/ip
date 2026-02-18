package quark.task;

import quark.save.SaveManager;

public class Deadline extends Task {
    public static final String PREFIX = "D";
    public static final String DEADLINE_FORMAT = "%1$s (by: %2$s)";


    private final String endDate;

    public Deadline(String description, String endDate) {
        super(description);
        this.endDate = endDate;
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    @Override
    public String toString() {
        return String.format(DEADLINE_FORMAT, getMetaDisplayString(), endDate);
    }

    @Override
    public String toSaveString() {
        return getMetaSaveString() + SaveManager.SAVE_DELIMITER + endDate;
    }
}
