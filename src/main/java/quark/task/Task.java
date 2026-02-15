package quark.task;

import quark.save.SaveManager;
import quark.save.Saveable;

public abstract class Task implements Saveable {
    public static final String IS_DONE_MARKER = "X";

    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public abstract String getPrefix();

    private String getIsDoneString() {
        return isDone ? IS_DONE_MARKER: " ";
    }

    private String getWrappedIsDoneString() {
        return "[" + getIsDoneString() + "]";
    }

    private String getWrappedPrefixString() {
        return "[" + getPrefix() + "]";
    }

    public String getMetaDisplayString() {
        return getWrappedIsDoneString()
                + getWrappedPrefixString()
                + " " + description;
    }

    public String getMetaSaveString () {
        return getIsDoneString() + SaveManager.SAVE_DELIMITER
                + getPrefix() + SaveManager.SAVE_DELIMITER + description;
    }
}
