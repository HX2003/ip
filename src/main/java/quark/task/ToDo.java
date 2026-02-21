package quark.task;

import java.util.ArrayList;

/**
 * Represents a simple ToDo task without any date constraints.
 * A ToDo task is the most basic type of task,
 * consisting only of a description and a completion status.
 *
 * @see Task
 */
public class ToDo extends Task {
    public static final String PREFIX = "T";

    /**
     * Constructs a new ToDo task with the specified description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    public String toString() {
        return getMetaDisplayString();
    }

    @Override
    public ArrayList<String> toSaveStrings() {
        return getMetaSaveStrings();
    }
}
