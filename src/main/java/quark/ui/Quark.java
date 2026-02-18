package quark.ui;

import quark.parser.Parser;
import quark.save.SaveManager;

import static quark.ui.Ui.printHelloReply;

public class Quark {
    private static Ui ui;
    private static Parser parser;

    public static void main(String[] args) {
        initialize();
        run();
    }

    private static void initialize() {
        ui = new Ui();
        SaveManager saveManager = new SaveManager();
        parser = new Parser(saveManager);

        printHelloReply(saveManager.isLoadedFromFile(), saveManager.getFilePath().toString());
    }


    private static void run() {
        boolean exit;
        do {
            String line = ui.getLine();
            exit = parser.parse(line);
        } while(!exit);
    }
}
