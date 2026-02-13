package quark.task;

public class Deadline extends Task {
    private final String endDate;

    public Deadline(String description, String endDate) {
        super(description);
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "[D]" + getStatusString() + " " + description + " (by: " + endDate + ")";
    }
}
