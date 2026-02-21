package quark.task;

import quark.save.Saveable;

import java.util.ArrayList;

/**
 * Represents an abstract task,
 * that provides common functionality for task status management and string formatting.
 *
 * @see Saveable
 */
public abstract class Task implements Saveable {
    public static final String IS_DONE_MARKER = "X";
    public static final String FORMAT_WRAP = "[%1$s]";

    /** The textual description of this task */
    private final String description;

    /** The completion status of this task (true if completed, false otherwise) */
    private boolean isDone;

    /**
     * Constructs a new Task with the specified description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Sets the completion status of this task.
     *
     * @param isDone The new completion status (true for completed, false for not completed).
     */
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Returns the description of this task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the prefix string identifier for this task type.
     * Each concrete task type should provide its own unique prefix.
     */
    public abstract String getPrefix();

    /**
     * Returns the prefix string identifier for this task type wrapped in characters.
     */
    private String getWrappedPrefixString() {
        return String.format(FORMAT_WRAP, getPrefix());
    }

    /**
     * Returns the completion status marker as a string.
     *
     * @return {@value #IS_DONE_MARKER} if completed, otherwise a space character.
     */
    private String getIsDoneString() {
        return isDone ? IS_DONE_MARKER: " ";
    }

    /**
     * Returns the completion status marker string wrapped in characters.
     */
    private String getWrappedIsDoneString() {
        return String.format(FORMAT_WRAP, getIsDoneString());
    }

    /**
     * Returns the formatted display string used for printing for this task.
     */
    public String getMetaDisplayString() {
        return getWrappedPrefixString()
                + getWrappedIsDoneString()
                + " " + description;
    }

    /**
     * Returns an ArrayList of strings that can be used
     * to save and reconstruct the task later.
     */
    public ArrayList<String> getMetaSaveStrings() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(getPrefix());
        strings.add(getIsDoneString());
        strings.add(description);
        return strings;
    }
}
