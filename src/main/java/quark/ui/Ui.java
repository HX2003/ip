package quark.ui;

public class Ui {
    private static final String SEPARATOR = "____________________________________________________________";

    public static void printReply(String reply) {
        System.out.println(SEPARATOR + System.lineSeparator()
                + reply + System.lineSeparator()
                + SEPARATOR);
    }

    public static void printHelloReply() {
        printReply("Hello! I'm Quark" + System.lineSeparator()
                + "What can I do for you?");
    }

    public static void printByeReply() {
        printReply("Bye. Hope to see you again soon!");
    }
}
