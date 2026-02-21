package quark.parser;

import quark.exception.QuarkCommandException;
import quark.command.Command;
import quark.save.SaveManager;
import quark.save.SaveState;
import quark.task.Deadline;
import quark.task.Event;
import quark.task.Task;
import quark.task.ToDo;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static quark.ui.Ui.printByeReply;
import static quark.ui.Ui.printFailedToAnnihilateReply;
import static quark.ui.Ui.printFailedToSaveReply;
import static quark.ui.Ui.printReply;

/**
 * Handles the parsing and execution of commands,
 * based on provided input strings.
 */
public class Parser {
    /** The keyword used to indicate an endDate in deadline command */
    private static final String PREFIX_BY = " /by ";

    /** The keyword used to indicate an endDate in event command */
    private static final String PREFIX_TO = " /to ";

    /** The keyword used to indicate an startDate in event command */
    private static final String PREFIX_FROM = " /from ";

    private final SaveManager saveManager;
    private final SaveState saveState;

    /**
     * Constructs a new Parser with the specified save manager.
     * Initializes the parser with the current save state from the manager.
     *
     * @param saveManager The SaveManager instance to be used for
     *                    obtaining and managing the save state.
     */
    public Parser(SaveManager saveManager) {
        this.saveManager = saveManager;
        this.saveState = saveManager.getState();
    }

    /**
     * Parses a task index from the given command arguments.
     * The arguments should contain a valid integer representing the task number.
     *
     * @param arguments The command arguments containing the task index.
     * @return The parsed task index as a zero-based integer.
     * @throws QuarkCommandException If the argument is in an invalid format,
     *         such as when it is empty, contain non-numeric characters,
     *         or represent a number out of range.
     */
    private int parseTaskIndex(String arguments) throws QuarkCommandException {
        int id;
        try {
            id = Integer.parseInt(arguments) - 1;
        } catch (NumberFormatException e) {
            throw new QuarkCommandException("Failed to parse task number");
        }

        if (id < 0 || id >= saveState.getTasks().size()) {
            throw new QuarkCommandException("Task number out of range");
        }
        return id;
    }

    /**
     * Handles an empty command.
     */
    private void handleEmptyCommand() {
        printReply("Command not recognized, did you mean to enter command?");
    }

    /**
     * Handles an unrecognizable command.
     *
     * @param command The unrecognized command string entered by the user.
     */
    private void handleUnrecognizableCommand(String command) {
        printReply("Command \"" + command + "\" not recognized");
    }

    /**
     * Handles a bye command.
     */
    private void handleByeCommand() {
        if (!saveManager.save()) {
            printFailedToSaveReply();
        }
        printByeReply();
    }

    /**
     * Handles an annihilate command, for deleting the save file.
     */
    private void handleAnnihilateCommand() {
        printReply("Attempting to delete from disk");
        if (!saveManager.annihilate()) {
            printFailedToAnnihilateReply();
        }
        printByeReply();
    }

    /**
     * Handles a list command, for listing all tasks.
     */
    private void handleListCommand()  {
        StringBuilder reply = new StringBuilder();
        if (saveState.getTasks().isEmpty()) {
            reply.append("You have no tasks");
        } else {
            reply.append("Here are the tasks:");
        }
        for (int i = 0; i < saveState.getTasks().size(); i++) {
            String taskString = saveState.getTasks().get(i).toString();
            String line = System.lineSeparator() + (i + 1) + ". " + taskString;
            reply.append(line);
        }
        printReply(reply.toString());
    }

    /**
     * Handles a find command,
     * by searching for tasks with description containing the specified string
     * and displaying all matching tasks to the user.
     *
     *  @param ignoredCommand The command string which is not used in this method.
     *  @param arguments The search string used to filter tasks by description.
     *  @throws QuarkCommandException If the argument is in an invalid format,
     *          such as when it is empty.
     */
    private void handleFindCommand(String ignoredCommand, String arguments)
            throws QuarkCommandException {
        if (arguments.isBlank()) {
            throw new QuarkCommandException("Your argument is blank");
        }

        ArrayList<Task> filteredTasks = saveState.getTasks()
                .stream()
                .filter(t -> t.getDescription().contains(arguments))
                .collect(Collectors.toCollection(ArrayList::new));

        StringBuilder reply = new StringBuilder();
        if (filteredTasks.isEmpty()) {
            reply.append("No matching tasks found");
        } else {
            reply.append("Here are the matching tasks:");
        }

        for (int i = 0; i < filteredTasks.size(); i++) {
            String line = System.lineSeparator() + filteredTasks.get(i);
            reply.append(line);
        }
        printReply(reply.toString());
    }

    /**
     * Handles task-related commands for creating new task,
     * they include the todo, deadline and event command.
     *
     * @param command The command string which is either todo, deadline or event.
     * @param arguments The various additional arguments.
     * @throws QuarkCommandException If argument is in invalid format.
     */
    private void handleTaskCommand(String command, String arguments)
            throws QuarkCommandException {
        if (arguments.contains(SaveManager.SAVE_DELIMITER)) {
            throw new QuarkCommandException(
                    "Your argument should not contain special character "
                    + SaveManager.SAVE_DELIMITER);
        }

        switch (command) {
        case Command.TODO -> {
            ToDo task = new ToDo(arguments);
            saveState.getTasks().add(task);
        }
        case Command.DEADLINE -> {
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
        case Command.EVENT -> {
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

    /**
     * Handles mark command, for setting a task as completed.
     *
     * @param ignoredCommand The command string which is not used in this method.
     * @param arguments The index of the task starting from 1.
     * @throws QuarkCommandException If argument is in invalid format
     */
    private void handleMarkCommand(String ignoredCommand, String arguments)
            throws QuarkCommandException  {
        int id = parseTaskIndex(arguments);
        saveState.getTasks().get(id).setDone(true);
        printReply("Nice! I've marked this task as done:" + System.lineSeparator()
                    + saveState.getTasks().get(id));
    }

    /**
     * Handles unmark command, for setting a task as not completed.
     *
     * @param ignoredCommand The command string which is not used in this method.
     * @param arguments The index of the task starting from 1.
     * @throws QuarkCommandException If argument is in invalid format
     */
    private void handleUnmarkCommand(String ignoredCommand, String arguments)
            throws QuarkCommandException  {
        int id = parseTaskIndex(arguments);
        saveState.getTasks().get(id).setDone(false);
        printReply("OK, I've marked this task as not done yet:" + System.lineSeparator()
                + saveState.getTasks().get(id));
    }

    /**
     * Handles a delete command, for removing task.
     *
     *  @param ignoredCommand The command string which is not used in this method.
     *  @param arguments The index of the task starting from 1.
     *  @throws QuarkCommandException If the argument is in an invalid format.
     */
    private void handleDeleteCommand(String ignoredCommand, String arguments)
            throws QuarkCommandException {
        int id = parseTaskIndex(arguments);

        Task task = saveState.getTasks().remove(id);
        printReply("Ok! I've removed this task:" + System.lineSeparator()
                + task + System.lineSeparator()
                + "You now have " + saveState.getTasks().size() + " tasks in total.");
    }

    /**
     * Parses and executes the command intended by raw user input.
     *
     * @param in The raw input string from the user.
     * @return true if the command indicates the program should exit (bye or annihilate commands),
     *         false otherwise.
     */
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
            case Command.BLANK -> handleEmptyCommand();
            case Command.BYE -> {
                handleByeCommand();
                return true;
            }
            case Command.ANNIHILATE -> {
                handleAnnihilateCommand();
                return true;
            }
            case Command.LIST -> handleListCommand();
            case Command.FIND -> handleFindCommand(command, argument);
            case Command.TODO, Command.DEADLINE, Command.EVENT -> handleTaskCommand(command, argument);
            case Command.MARK -> handleMarkCommand(command, argument);
            case Command.UNMARK -> handleUnmarkCommand(command, argument);
            case Command.DELETE -> handleDeleteCommand(command, argument);
            default -> handleUnrecognizableCommand(command);
            }
        } catch (QuarkCommandException e) {
            printReply("Unable to decipher command arguments: " + e.getMessage());
        }

        return false;
    }
}
