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

    private static void printHelloMessage() {
        String helloMessage = "Hello! I'm Quark" + System.lineSeparator()
                + "What can I do for you?" + System.lineSeparator()
                + SEPARATOR;

        System.out.println(helloMessage);
    }

    private static void printByeMessage() {
        String byeMessage = SEPARATOR + System.lineSeparator()
                + "Bye. Hope to see you again soon!" + System.lineSeparator()
                + SEPARATOR;

        System.out.println(byeMessage);
    }

    public static void handleEmptyCommand() {
        printReply("Command not recognized, did you mean to a enter command?");
    }

    public static void handleUnrecognizableCommand(String command) {
        printReply("Command \"" + command + "\" not recognized");
    }

    public static void handleListCommand()  {
        System.out.println(SEPARATOR);
        if (tasks.isEmpty()) {
            System.out.println("You have no tasks");
        } else {
            System.out.println("Here are the tasks:");
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        System.out.println(SEPARATOR);
    }

    public static void handleTaskCommand(String command, String arguments) {
        switch (command) {
            case "todo" -> {
                ToDo task = new ToDo(arguments);
                tasks.add(task);
            }
            case "deadline" -> {
                int indexOfBy = arguments.indexOf(PREFIX_BY);

                if (indexOfBy == -1) {
                    return;
                }

                String description = arguments.substring(0, indexOfBy);
                String endDate = arguments.substring(indexOfBy + PREFIX_BY.length());

                if (description.isBlank() || endDate.isBlank()) {
                    return;
                }

                Deadline task = new Deadline(description, endDate);
                tasks.add(task);
            }
            case "event" -> {
                int indexOfFrom = arguments.indexOf(PREFIX_FROM);
                int indexOfTo = arguments.indexOf(PREFIX_TO);

                if (indexOfFrom == -1 || indexOfTo == -1 || indexOfFrom >= indexOfTo) {
                    return;
                }

                String description = arguments.substring(0, indexOfFrom);
                String startDate = arguments.substring(indexOfFrom + PREFIX_FROM.length(), indexOfTo);
                String endDate = arguments.substring(indexOfTo + PREFIX_TO.length());

                if (description.isBlank() || startDate.isBlank() || endDate.isBlank()) {
                    return;
                }

                Event task = new Event(description, startDate, endDate);
                tasks.add(task);
            }
        }

        System.out.println(SEPARATOR + System.lineSeparator()
                + "Added this task:" + System.lineSeparator()
                + tasks.get(tasks.size() - 1) + System.lineSeparator()
                + "You now have " + tasks.size() + " tasks in total." + System.lineSeparator()
                + SEPARATOR);
    }

    public static void handleMarkUnmarkCommand(String command, String arguments)  {
        boolean isMark = command.equals("mark");
        try {
            int id = Integer.parseInt(arguments) - 1;

            if (id < 0 || id >= tasks.size()) {
                return;
            }

            tasks.get(id).setDone(isMark);
            String reply;
            if (isMark) {
                reply = SEPARATOR + System.lineSeparator()
                        + "Nice! I've marked this task as done:" + System.lineSeparator()
                        + tasks.get(id) + System.lineSeparator()
                        + SEPARATOR;
            } else {
                reply = SEPARATOR + System.lineSeparator()
                        + "OK, I've marked this task as not done yet:" + System.lineSeparator()
                        + tasks.get(id) + System.lineSeparator()
                        + SEPARATOR;
            }
            System.out.println(reply);
        } catch (NumberFormatException e) {
            // Exception
        }
    }

    public static boolean parser(String in) {
        String line = in.trim();
        String[] split = line.split(" ", 2); // Split into command, and arguments

        String command = split[0];

        switch (command) {
            case "" -> handleEmptyCommand();
            case "bye" -> {
                return true;
            }
            case "list" -> handleListCommand();
            case "todo", "deadline", "event" -> handleTaskCommand(command, split[1]);
            case "mark", "unmark" -> handleMarkUnmarkCommand(command, split[1]);
            default -> handleUnrecognizableCommand(command);
        }

        return false;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        printHelloMessage();

        while (true) {
            if (in.hasNextLine()) {
                if (parser(in.nextLine())) break;
            }
        }

        printByeMessage();
    }
}
