package quark.ui;

import java.util.Scanner;

public class Ui {
    private static final String SEPARATOR = "____________________________________________________________";
    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public String getLine() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return "";
    }

    public static void printReply(String... reply) {
        System.out.println(SEPARATOR);
        for (String r : reply) {
            System.out.println(r);
        }
        System.out.println(SEPARATOR);
    }

    public static void printHelloReply(boolean isLoadedFromFile, String path) {
        if (isLoadedFromFile) {
            printReply("Hello again! I'm Quark",
                    "I have loaded your tasks from " + path,
                    "what can I do for you?");
        } else {
            printReply("Hello! I'm Quark",
                    "Looks like you're new here, what can I do for you?");
        }
    }

    public static void printFailedToSaveReply(String errorString) {
        printReply("Oh o, something went wrong while saving: " + errorString);
    }

    public static void printFailedToAnnihilateReply(String errorString) {
        printReply("I could not delete from disk: " + errorString);
    }

    public static void printByeReply() {
        printReply("Bye. Hope to see you again soon!");
    }
}
