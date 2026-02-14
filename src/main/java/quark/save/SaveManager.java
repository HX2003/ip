package quark.save;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;

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
        try {
            File file = new File(filePath.toUri());

            System.out.println(file);
            Scanner s = new Scanner(file);
            while (s.hasNext()) {
                System.out.println(s.nextLine());
            }

            isLoadedFromFile = true;
        } catch (FileNotFoundException e) {
            isLoadedFromFile = false;
            state = new SaveState();
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
