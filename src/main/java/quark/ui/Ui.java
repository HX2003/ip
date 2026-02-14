package quark.ui;

public class Ui {
    private static final String SEPARATOR = "____________________________________________________________";

    public static void printReply(String reply) {
        System.out.println(SEPARATOR + System.lineSeparator()
                + reply + System.lineSeparator()
                + SEPARATOR);
    }

    public static void printHelloReply(boolean isLoadedFromFile, String path) {
        if (isLoadedFromFile) {
            printReply("Hello again! I'm Quark" + System.lineSeparator()
                    + "I have loaded your tasks from " + path + System.lineSeparator()
                    + "what can I do for you?");
        } else {
            printReply("Hello! I'm Quark" + System.lineSeparator()
                    + "Looks like you're new here, what can I do for you?");
        }
    }

    public static void printFailedToSaveReply(String errorString) {
        printReply("Oh o, something went wrong while saving: " + errorString);
    }

    public static void printByeReply() {
        printReply("Bye. Hope to see you again soon!");
    }
}
