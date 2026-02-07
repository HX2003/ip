import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Quark {
    static List<Task> tasks = new ArrayList<>();

    private static final String SEPARATOR = "____________________________________________________________";
    private static final String PREFIX_BY = " /by ";
    private static final String PREFIX_TO = " /to ";
    private static final String PREFIX_FROM = " /from ";

    private static void printReply(String reply) {
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

    public static void handleEmptyCommand() {
        printReply("Command not recognized, did you mean to a enter command?");
    }

    public static void handleUnrecognizableCommand(String command) {
        printReply("Command \"" + command + "\" not recognized");
    }

    public static void handleListCommand()  {
        StringBuilder reply = new StringBuilder();
        if (tasks.isEmpty()) {
            reply.append("You have no tasks");
        } else {
            reply.append("Here are the tasks:");
        }
        for (int i = 0; i < tasks.size(); i++) {
            String line = System.lineSeparator() + (i + 1) + ". " + tasks.get(i);
            reply.append(line);
        }
        printReply(reply.toString());
    }

    public static void handleTaskCommand(String command, String arguments) throws QuarkCommandException {
        switch (command) {
            case "todo" -> {
                ToDo task = new ToDo(arguments);
                tasks.add(task);
            }
            case "deadline" -> {
                int indexOfBy = arguments.indexOf(PREFIX_BY);

                if (indexOfBy == -1) {
                    throw new QuarkCommandException("Failed to parse provided deadline");
                }

                String description = arguments.substring(0, indexOfBy);
                String endDate = arguments.substring(indexOfBy + PREFIX_BY.length());

                if (description.isBlank() || endDate.isBlank()) {
                    throw new QuarkCommandException("Failed to parse provided deadline");
                }

                Deadline task = new Deadline(description, endDate);
                tasks.add(task);
            }
            case "event" -> {
                int indexOfFrom = arguments.indexOf(PREFIX_FROM);
                int indexOfTo = arguments.indexOf(PREFIX_TO);

                if (indexOfFrom == -1 || indexOfTo == -1 || indexOfFrom >= indexOfTo) {
                    throw new QuarkCommandException("Failed to parse provided event");
                }

                String description = arguments.substring(0, indexOfFrom);
                String startDate = arguments.substring(indexOfFrom + PREFIX_FROM.length(), indexOfTo);
                String endDate = arguments.substring(indexOfTo + PREFIX_TO.length());

                if (description.isBlank() || startDate.isBlank() || endDate.isBlank()) {
                    throw new QuarkCommandException("Failed to parse provided event");
                }

                Event task = new Event(description, startDate, endDate);
                tasks.add(task);
            }
        }

        printReply("Added this task:" + System.lineSeparator()
                + tasks.get(tasks.size() - 1) + System.lineSeparator()
                + "You now have " + tasks.size() + " tasks in total.");
    }

    public static void handleMarkUnmarkCommand(String command, String arguments) throws QuarkCommandException  {
        boolean isMark = command.equals("mark");
        int id;
        try {
            id = Integer.parseInt(arguments) - 1;
        } catch (NumberFormatException e) {
            throw new QuarkCommandException("Failed to parse task number");
        }

        if (id < 0 || id >= tasks.size()) {
            throw new QuarkCommandException("Task number out of range");
        }

        tasks.get(id).setDone(isMark);
        if (isMark) {
            printReply("Nice! I've marked this task as done:" + System.lineSeparator()
                    + tasks.get(id));
        } else {
            printReply("OK, I've marked this task as not done yet:" + System.lineSeparator()
                    + tasks.get(id));
        }
    }

    public static boolean parser(String in) {
        String line = in.trim();
        String[] split = line.split(" ", 2); // Split into command, and arguments

        String command = split[0];
        String argument = "";

        if (split.length > 1) {
            argument = split[1];
        }

        try {
            switch (command) {
            case "" -> handleEmptyCommand();
            case "bye" -> {
                return true;
            }
            case "list" -> handleListCommand();
            case "todo", "deadline", "event" -> handleTaskCommand(command, argument);
            case "mark", "unmark" -> handleMarkUnmarkCommand(command, argument);
            default -> handleUnrecognizableCommand(command);
            }
        } catch (QuarkCommandException e) {
            printReply("Unable to decipher command arguments: " + e.getMessage());
        }

        return false;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        printHelloReply();

        while (true) {
            if (in.hasNextLine()) {
                if (parser(in.nextLine())) break;
            }
        }

        printByeReply();
    }
}
