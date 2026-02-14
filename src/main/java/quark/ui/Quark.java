package quark.ui;

import quark.parser.Parser;
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
    public static List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        printHelloReply();

        Parser parser = new Parser();
        parser.loop();

        printByeReply();
    }
}
