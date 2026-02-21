package quark;

import quark.parser.Parser;
import quark.save.SaveManager;
import quark.ui.Ui;

import static quark.ui.Ui.printHelloReply;

/**
 * Entry point of the Quark application.
 * Initializes the application and starts the interactive session with the user.
 */
public class Quark {
    private static Ui ui;
    private static SaveManager saveManager;

    public static void main(String[] args) {
        initialize();
        run();
    }

    /**
     * Sets up the required objects, and prints the hello message.
     */
    private static void initialize() {
        ui = new Ui();
        saveManager = new SaveManager();
        printHelloReply(saveManager.isLoadedFromFile(), saveManager.getFilePath().toString());
    }

    /**
     * Runs the program until user requests to terminate the program
     * by either using the bye or annihilate command.
     */
    private static void run() {
        boolean isExit;
        do {
            String line = ui.getLine();
            isExit = new Parser(saveManager).parse(line);
        } while(!isExit);
    }
}
