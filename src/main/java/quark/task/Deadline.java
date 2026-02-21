package quark.task;

import java.util.ArrayList;

/**
 * Represents a Deadline task with end date constraints,
 * as well as a description and completion status.
 *
 * @see Task
 */
public class Deadline extends Task {
    public static final String PREFIX = "D";
    public static final String FORMAT_DEADLINE = "%1$s (by: %2$s)";

    /** The end date of the task, for when it should be completed by */
    private final String endDate;

    /**
     * Constructs a new Deadline task with the specified
     * end date constraints, as well as description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
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
        return String.format(FORMAT_DEADLINE, getMetaDisplayString(), endDate);
    }

    @Override
    public ArrayList<String> toSaveStrings() {
        ArrayList<String> strings = getMetaSaveStrings();
        strings.add(endDate);
        return strings;
    }
}
