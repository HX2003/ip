package quark.save;

import quark.task.Task;
import quark.task.Deadline;
import quark.task.Event;
import quark.task.ToDo;

import static quark.task.Task.IS_DONE_MARKER;
import static quark.ui.Ui.printFailedToAnnihilateReply;
import static quark.ui.Ui.printFailedToSaveReply;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.regex.Pattern;

public class SaveManager {
    public static final String SAVE_DELIMITER = "|";
    public static final int SAVE_TASK_IS_DONE_IDX = 0;
    public static final int SAVE_TASK_PREFIX_IDX = 1;
    public static final int SAVE_TASK_DESCRIPTION_IDX = 2;
    public static final int SAVE_TASK_MAX_IDX = SAVE_TASK_DESCRIPTION_IDX;

    public static final int SAVE_DEADLINE_END_DATE_IDX = 3;
    public static final int SAVE_DEADLINE_MAX_IDX = SAVE_DEADLINE_END_DATE_IDX;

    public static final int SAVE_EVENT_START_DATE_IDX = 3;
    public static final int SAVE_EVENT_END_DATE_IDX = 4;
    public static final int SAVE_EVENT_MAX_IDX = SAVE_EVENT_END_DATE_IDX;


    private static final String[] FILE_SAVE_PATH = {"quark", "save.txt"};

    private Path filePath;
    private SaveState state;
    private boolean isLoadedFromFile;

    public SaveManager() {
        initializeFilePath();
        loadSave();
    }

    private void initializeFilePath () {
        String home = System.getProperty("user.home");
        filePath = Path.of(home, FILE_SAVE_PATH);
    }

    private void loadSave() {
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            state = createSaveState(reader);
            isLoadedFromFile = true;
        } catch (IOException e) {
            isLoadedFromFile = false;
            state = new SaveState();
        }
    }

    private SaveState createSaveState(BufferedReader reader) throws IOException {
        SaveState state = new SaveState();
        String line;
        while ((line = reader.readLine()) != null) {
            Task task = createTask(line);
            if (task != null) {
                state.getTasks().add(task);
            }
        }

        return state;
    }

    private Task createTask(String in) {
        String splitBy = Pattern.quote(SAVE_DELIMITER);
        String[] split = in.split(splitBy);

        int splitMaxIdx = split.length - 1;

        if (splitMaxIdx < SAVE_TASK_MAX_IDX) {
            return null;
        }

        boolean isDone = split[SAVE_TASK_IS_DONE_IDX].equals(IS_DONE_MARKER);
        String prefix = split[SAVE_TASK_PREFIX_IDX];
        String description = split[SAVE_TASK_DESCRIPTION_IDX];

        switch (prefix) {
        case ToDo.PREFIX -> {
            Task task = new ToDo(description);
            task.setDone(isDone);
            return task;
        }
        case Deadline.PREFIX -> {
            if (splitMaxIdx < SAVE_DEADLINE_MAX_IDX) {
                return null;
            }
            String endDate = split[SAVE_DEADLINE_END_DATE_IDX];
            Task task = new Deadline(description, endDate);
            task.setDone(isDone);
            return task;
        }
        case Event.PREFIX -> {
            if (splitMaxIdx < SAVE_EVENT_MAX_IDX) {
                return null;
            }

            String startDate = split[SAVE_EVENT_START_DATE_IDX];
            String endDate = split[SAVE_EVENT_END_DATE_IDX];
            Task task = new Event(description, startDate, endDate);
            task.setDone(isDone);
            return task;
        }
        default -> {
            return null;
        }
        }
    }

    public void save() {
        // Create directory for the file if not exist
        try {
            Files.createDirectories(filePath.getParent());
        } catch (IOException e) {
            printFailedToSaveReply(e.toString());
        }

        // Try to write task data into a file,
        // it will automatically create a new file if it does not exist
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Task task: state.getTasks()) {
                writer.write(task.toSaveString());
                writer.newLine();
            }
        } catch (IOException e) {
            printFailedToSaveReply(e.toString());
        }
    }

    public void annihilate() {
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            printFailedToAnnihilateReply(e.toString());
        }
    }

    public boolean isLoadedFromFile() {
        return isLoadedFromFile;
    }

    public Path getFilePath() {
        return filePath;
    }

    public SaveState getState() {
        return state;
    }
}
