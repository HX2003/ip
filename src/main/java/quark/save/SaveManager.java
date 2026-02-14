package quark.save;

import quark.task.Task;

import static quark.ui.Ui.printFailedToSaveReply;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class SaveManager {
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
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            isLoadedFromFile = true;
        } catch (IOException e) {
            isLoadedFromFile = false;
            state = new SaveState();
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
