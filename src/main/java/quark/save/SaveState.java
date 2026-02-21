package quark.save;

import quark.task.Task;

import java.util.ArrayList;

/**
 * Represents the state of the Quark application at a point in time.
 * Contains a collection of tasks that can be saved to or loaded from persistent storage.
 */
public class SaveState {
    /** A list of tasks */
    private final ArrayList<Task> tasks;

    /**
     * Constructs a new empty save state with no tasks.
     */
    public SaveState() {
        tasks = new ArrayList<>();
    }

    /**
     * Returns the list of tasks in this save state.
     *
     * @return An ArrayList containing all tasks in the save state.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
