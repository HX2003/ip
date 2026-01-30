import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Quark {
    static List<Task> tasks = new ArrayList<>();

    private static final String SEPARATOR = "____________________________________________________________";
    private static final String PREFIX_BY = " /by ";
    private static final String PREFIX_TO = " /to ";
    private static final String PREFIX_FROM = " /from ";

    public static void executeListCommand(String command)  {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public static void executeTaskCommand(String command, String arguments) {
        switch (command) {
            case "todo" -> {
                ToDo task = new ToDo(arguments);
                tasks.add(task);
            }
            case "deadline" -> {
                int indexOfBy = arguments.indexOf(PREFIX_BY);

                if (indexOfBy == -1) return;

                String description = arguments.substring(0, indexOfBy);
                String endDate = arguments.substring(indexOfBy + PREFIX_BY.length());

                if (description.isBlank() || endDate.isBlank()) return;

                Deadline task = new Deadline(description, endDate);
                tasks.add(task);
            }
            case "event" -> {
                int indexOfFrom = arguments.indexOf(PREFIX_FROM);
                int indexOfTo = arguments.indexOf(PREFIX_TO);

                if (indexOfFrom == -1 || indexOfTo == -1 || indexOfFrom >= indexOfTo) return;

                String description = arguments.substring(0, indexOfFrom);
                String startDate = arguments.substring(indexOfFrom + PREFIX_FROM.length(), indexOfTo);
                String endDate = arguments.substring(indexOfTo + PREFIX_TO.length());

                if (description.isBlank() || startDate.isBlank() || endDate.isBlank()) return;

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

    public static void executeMarkUnmarkCommand(String command, String arguments)  {
        boolean isMark = command.equals("mark");
        try {
            int id = Integer.parseInt(arguments) - 1;

            if (id < 0 || id >= tasks.size()) return;

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

        if (split.length < 1) return false;

        String command = split[0];

        // Handle input commands without arguments first
        if (command.equals("bye")) {
            return true;
        } else if (command.equals("list")) {
            executeListCommand(command);
        }

        // Handle input commands with arguments
        if (split.length < 2) return false;

        if (command.equals("todo") || command.equals("deadline") || command.equals("event")) {
            executeTaskCommand(command, split[1]);
        } else if (command.equals("mark") || command.equals("unmark")) {
            executeMarkUnmarkCommand(command, split[1]);
        }

        return false;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String logo = "Hello! I'm Quark" + System.lineSeparator()
                + "What can I do for you?" + System.lineSeparator()
                + SEPARATOR;

        System.out.println(logo);

        while (true) {
            if (in.hasNextLine()) {
                if (parser(in.nextLine())) break;
            }
        }

        String byeReply = SEPARATOR + System.lineSeparator()
                + "Bye. Hope to see you again soon!" + System.lineSeparator()
                + SEPARATOR;

        System.out.println(byeReply);
    }
}
