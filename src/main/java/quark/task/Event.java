package quark.task;

public class Event extends Task {
    private final String startDate;
    private final String endDate;

    public Event(String description, String startDate, String endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "[E]" + getStatusString() + " " + description + " (from: " + startDate + ", to: " + endDate + ")";
    }
}
