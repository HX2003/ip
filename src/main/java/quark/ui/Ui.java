package quark.ui;

import java.util.Scanner;

/**
 * Handles all user interface interactions for the Quark application.
 * Provides methods for reading user input and displaying formatted messages
 * with consistent visual separation.
 */
public class Ui {
    /** The visual separator line */
    private static final String SEPARATOR = "____________________________________________________________";

    /** Scanner instance for reading user input from the console */
    private final Scanner scanner;

    /**
     * Constructs a new Ui instance and initializes the input scanner.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Returns the next line string of user input from the console,
     * or an empty string if no input is available.
     */
    public String getLine() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return "";
    }

    /**
     * Prints a formatted message to the user with visual separators.
     * Each string in the varargs array is printed on a new line.
     *
     * @param reply The reply lines to be printed.
     */
    public static void printReply(String... reply) {
        System.out.println(SEPARATOR);
        for (String r : reply) {
            System.out.println(r);
        }
        System.out.println(SEPARATOR);
    }

    /**
     * Prints a hello message to the user,
     * which is contextualized on whether
     * previously saved tasks from the disk is available
     *
     * @param isLoadedFromFile If tasks were successfully loaded from a file.
     * @param path The file path where tasks were loaded from (or would be saved to).
     */
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

    /**
     * Prints an error message indicating that saving tasks to disk has failed.
     */
    public static void printFailedToSaveReply() {
        printReply("Oh o, something went wrong while saving");
    }

    /**
     * Prints an error message indicating that deleting tasks from disk has failed.
     */
    public static void printFailedToAnnihilateReply() {
        printReply("I could not delete from disk");
    }

    /**
     * Prints a goodbye message.
     */
    public static void printByeReply() {
        printReply("Bye. Hope to see you again soon!");
    }
}
