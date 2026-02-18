package quark.task;

import java.util.ArrayList;

public class Deadline extends Task {
    public static final String PREFIX = "D";
    public static final String FORMAT_DEADLINE = "%1$s (by: %2$s)";


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
        return String.format(FORMAT_DEADLINE, getMetaDisplayString(), endDate);
    }

    @Override
    public ArrayList<String> toSaveStrings() {
        ArrayList<String> strings = getMetaSaveStrings();
        strings.add(endDate);
        return strings;
    }
}
