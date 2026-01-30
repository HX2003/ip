public class Deadline extends Task {
    private final String endDate;

    public Deadline(String description, String endDate) {
        super(description);
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        if (endDate.isBlank()) {
            throw new IllegalArgumentException();
        }

        return "[T]" + getStatusString() + " " + description + " (by: " + endDate + ")";
    }
}
