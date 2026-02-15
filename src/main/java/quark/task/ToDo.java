package quark.task;

public class ToDo extends Task {
    public static final String PREFIX = "T";

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
    public String toSaveString() {
        return getMetaSaveString();
    }
}
