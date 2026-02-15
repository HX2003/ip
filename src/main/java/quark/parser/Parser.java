package quark.parser;

import quark.QuarkCommandException;
import quark.save.SaveManager;
import quark.save.SaveState;
import quark.task.Deadline;
import quark.task.Event;
import quark.task.ToDo;

import static quark.ui.Ui.printReply;

import java.util.Scanner;


public class Parser {
    private static final String PREFIX_BY = " /by ";
    private static final String PREFIX_TO = " /to ";
    private static final String PREFIX_FROM = " /from ";

    private final SaveManager saveManager;
    private final SaveState saveState;

    public Parser(SaveManager saveManager) {
        this.saveManager = saveManager;
        this.saveState = saveManager.getState();
    }

    private void handleEmptyCommand() {
        printReply("Command not recognized, did you mean to a enter command?");
    }

    private void handleUnrecognizableCommand(String command) {
        printReply("Command \"" + command + "\" not recognized");
    }

    private void handleAnnihilateCommands() {
        saveManager.annihilate();
    }

    private void handleListCommand()  {
        StringBuilder reply = new StringBuilder();
        if (saveState.getTasks().isEmpty()) {
            reply.append("You have no tasks");
        } else {
            reply.append("Here are the tasks:");
        }
        for (int i = 0; i < saveState.getTasks().size(); i++) {
            String line = System.lineSeparator() + (i + 1) + ". " + saveState.getTasks().get(i);
            reply.append(line);
        }
        printReply(reply.toString());
    }

    private void handleTaskCommand(String command, String arguments) throws QuarkCommandException {
        switch (command) {
        case "todo" -> {
            ToDo task = new ToDo(arguments);
            saveState.getTasks().add(task);
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
            saveState.getTasks().add(task);
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
            saveState.getTasks().add(task);
        }
        }

        int numTasks = saveState.getTasks().size();
        int lastIndex = numTasks - 1;
        printReply("Added this task:" + System.lineSeparator()
                + saveState.getTasks().get(lastIndex) + System.lineSeparator()
                + "You now have " + numTasks + " tasks in total.");
    }

    private void handleMarkUnmarkCommand(String command, String arguments) throws QuarkCommandException  {
        boolean isMark = command.equals("mark");
        int id;
        try {
            id = Integer.parseInt(arguments) - 1;
        } catch (NumberFormatException e) {
            throw new QuarkCommandException("Failed to parse task number");
        }

        if (id < 0 || id >= saveState.getTasks().size()) {
            throw new QuarkCommandException("Task number out of range");
        }

        saveState.getTasks().get(id).setDone(isMark);
        if (isMark) {
            printReply("Nice! I've marked this task as done:" + System.lineSeparator()
                    + saveState.getTasks().get(id));
        } else {
            printReply("OK, I've marked this task as not done yet:" + System.lineSeparator()
                    + saveState.getTasks().get(id));
        }
    }

    public boolean parse (String in) {
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
            case "annihilate" -> {
                handleAnnihilateCommands();
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

    public void loop() {
        Scanner in = new Scanner(System.in);

        while (true) {
            if (in.hasNextLine()) {
                if (parse(in.nextLine())) break;
            }
        }
    }
}
