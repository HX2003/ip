package quark.ui;

import quark.parser.Parser;
import quark.save.SaveManager;
import quark.task.Deadline;
import quark.task.Event;
import quark.QuarkCommandException;
import quark.task.Task;
import quark.task.ToDo;

import java.util.ArrayList;
import java.util.List;

import static quark.ui.Ui.printByeReply;
import static quark.ui.Ui.printHelloReply;

public class Quark {
    public static void main(String[] args) {
        SaveManager saveManager = new SaveManager();

        printHelloReply(saveManager.isLoadedFromFile(), saveManager.getFilePath().toString());

        Parser parser = new Parser(saveManager);
        parser.loop();
    }
}
