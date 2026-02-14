package quark.ui;

import quark.parser.Parser;
import quark.task.Deadline;
import quark.task.Event;
import quark.QuarkCommandException;
import quark.task.Task;
import quark.task.ToDo;

import java.util.ArrayList;
import java.util.List;

public class Quark {
    public static List<Task> tasks = new ArrayList<>();

    private static final String SEPARATOR = "____________________________________________________________";


    public static void printReply(String reply) {
        System.out.println(SEPARATOR + System.lineSeparator()
                + reply + System.lineSeparator()
                + SEPARATOR);
    }

    private static void printHelloReply() {
        printReply("Hello! I'm Quark" + System.lineSeparator()
                + "What can I do for you?");
    }

    private static void printByeReply() {
        printReply("Bye. Hope to see you again soon!");
    }

    public static void main(String[] args) {
        printHelloReply();

        Parser parser = new Parser();
        parser.loop();

        printByeReply();
    }
}
