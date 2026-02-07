package quark.task;

public class Task {
    protected final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public String toString() {
        return description; // "[" + "]" + getStatusString()
    }

    public String getDescription() {
        return description;
    }

    protected String getStatusString() {
        return (isDone ? "[X]" : "[ ]"); // mark done task with X
    }
}
