package quark.ui;

import quark.parser.Parser;
import quark.save.SaveManager;

import static quark.ui.Ui.printHelloReply;

public class Quark {
    public static void main(String[] args) {
        SaveManager saveManager = new SaveManager();

        printHelloReply(saveManager.isLoadedFromFile(), saveManager.getFilePath().toString());

        Parser parser = new Parser(saveManager);
        parser.loop();
    }
}
