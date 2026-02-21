package quark.task;

import java.util.ArrayList;

/**
 * Represents an Event task with start and end date constraints,
 * as well as a description and completion status.
 *
 * @see Task
 */
public class Event extends Task {
    public static final String PREFIX = "E";
    public static final String FORMAT_EVENT = "%1$s (from: %2$s, to: %3$s)";

    /** The start date of the task, for when it should begin */
    private final String startDate;
    /** The end date of the task, for when it should be completed by */
    private final String endDate;

    /**
     * Constructs a new Event task with the specified
     * start and end date constraints, as well as description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task.
     * @param startDate The endDate of the task.
     * @param endDate The endDate of the task.
     */
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
