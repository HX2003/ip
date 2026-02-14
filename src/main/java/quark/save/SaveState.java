package quark.save;

import quark.task.Task;

import java.util.ArrayList;

public class SaveState {
    ArrayList<Task> tasks;

    public SaveState() {
        tasks = new ArrayList<>();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
