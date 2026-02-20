package quark.task;

import quark.save.Saveable;

import java.util.ArrayList;

public abstract class Task implements Saveable {
    public static final String IS_DONE_MARKER = "X";
    public static final String FORMAT_WRAP = "[%1$s]";

    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public String getDescription() {
        return description;
    }

    public abstract String getPrefix();

    private String getIsDoneString() {
        return isDone ? IS_DONE_MARKER: " ";
    }

    private String getWrappedIsDoneString() {
        return String.format(FORMAT_WRAP, getIsDoneString());
    }

    private String getWrappedPrefixString() {
        return String.format(FORMAT_WRAP, getPrefix());
    }

    public String getMetaDisplayString() {
        return getWrappedPrefixString()
                + getWrappedIsDoneString()
                + " " + description;
    }

    public ArrayList<String> getMetaSaveStrings() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(getPrefix());
        strings.add(getIsDoneString());
        strings.add(description);
        return strings;
    }
}
