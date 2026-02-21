package quark.ui;

import quark.parser.Parser;
import quark.save.SaveManager;

import static quark.ui.Ui.printHelloReply;

public class Quark {
    private static Ui ui;
    private static SaveManager saveManager;

    public static void main(String[] args) {
        initialize();
        run();
    }

    private static void initialize() {
        ui = new Ui();
        saveManager = new SaveManager();
        printHelloReply(saveManager.isLoadedFromFile(), saveManager.getFilePath().toString());
    }


    private static void run() {
        boolean isExit;
        do {
            String line = ui.getLine();
            isExit = new Parser(saveManager).parse(line);
        } while(!isExit);
    }
}
