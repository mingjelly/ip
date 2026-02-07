package jellicent.parser;

import jellicent.task.TaskList;
import jellicent.task.ToDo;
import jellicent.task.Deadline;
import jellicent.task.Event;
import jellicent.task.Task;

import jellicent.command.ByeCommand;
import jellicent.command.Command;
import jellicent.command.CommandType;
import jellicent.command.DeadlineCommand;
import jellicent.command.DeleteCommand;
import jellicent.command.EventCommand;
import jellicent.command.ListCommand;
import jellicent.command.MarkCommand;
import jellicent.command.TodoCommand;
import jellicent.command.UnmarkCommand;
import jellicent.command.FindCommand;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Provides parsing utilities for the Jellicent application.
 * <p>
 * The {@code Parser} class is responsible for converting raw input strings
 * or saved file data into {@link Task} objects or {@link Command} objects
 * that the application can execute. It also handles validation and throws
 * {@link ParserException} for invalid formats or missing information.
 * </p>
 * <p>
 * Key functionalities include:
 * <ul>
 *     <li>Converting stored file strings into a {@link TaskList} via {@link #stringsIntoTasks(ArrayList)}</li>
 *     <li>Converting user input strings into executable {@link Command} objects via {@link #userInputIntoCommand(String)}</li>
 *     <li>Parsing and validating date/time strings for tasks</li>
 *     <li>Providing informative error messages when input is invalid or incomplete</li>
 * </ul>
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     ArrayList&lt;String&gt; savedData = storage.loadFileDataIntoList();
 *     TaskList tasks = Parser.stringsIntoTasks(savedData);
 *
 *     Command command = Parser.userInputIntoCommand("todo Read a book");
 *     String output = command.execute(tasks, ui, storage);
 * </pre>
 * </p>
 */
public class Parser {
    private static final Map<CommandType, String> ARG_REQUIRED_ERROR = Map.of(
            CommandType.MARK, "OOPS! Mark command requires an integer after",
            CommandType.UNMARK, "OOPS! Unmark command requires an integer after",
            CommandType.DELETE, "OOPS! Delete command requires an integer after",
            CommandType.TODO, "OOPS! The description of a todo cannot be empty.",
            CommandType.EVENT, "OOPS! An event requires a description, /from and /to timeframe!",
            CommandType.DEADLINE,"OOPS! A deadline requires a description and /by deadline!",
            CommandType.FIND, "OOPS! Find command requires a search word!"
    );


    /**
     * Converts loaded data into tasks.
     *
     * @param strings ArrayList of tasks' data from saved file.
     * @return Initialised TaskList at the start of program.
     */
    public static TaskList stringsIntoTasks(ArrayList<String> strings) throws ParserException {
        TaskList taskList = new TaskList();

        for (String taskString : strings) {
            String[] dataArray = taskString.trim().split("\\|");

            // Throw exception if only 1 word is read
            if (dataArray.length < 2) {
                throw new ParserException("Tasks are saved in incorrect format!");
            }
            Task task = switch (dataArray[0]) {
                    case "T" -> parseTodo(dataArray);
                    case "D" -> parseDeadline(dataArray);
                    case "E" -> parseEvent(dataArray);
                    default -> throw new ParserException("Unknown Task Type: " + dataArray[0]);
                };
            taskList.add(task);
        }
        return taskList;
    }

    /**
     * Converts a single input line into a command during program execution.
     *
     * @param string user input to tell the program what to do.
     *
     * @return Executable Command for program to perform various actions.
     */
    public static Command userInputIntoCommand(String string) throws ParserException {
        String[] commandInfo = string.split(" ", 2);
        CommandType keyCommand = parseKeyCommand(commandInfo[0]);
        return switch (keyCommand) {
            case BYE -> new ByeCommand();
            case LIST -> new ListCommand();
            case MARK -> parseMarkCommand(getArgumentOrThrow(commandInfo, keyCommand));
            case UNMARK -> parseUnmarkCommand(getArgumentOrThrow(commandInfo, keyCommand));
            case DELETE -> parseDeleteCommand(getArgumentOrThrow(commandInfo, keyCommand));
            case TODO -> new TodoCommand(getArgumentOrThrow(commandInfo, keyCommand));
            case EVENT -> parseEventCommand(getArgumentOrThrow(commandInfo, keyCommand));
            case DEADLINE -> parseDeadlineCommand(getArgumentOrThrow(commandInfo, keyCommand));
            case FIND -> new FindCommand(getArgumentOrThrow(commandInfo, keyCommand));
            default -> throw new ParserException("Unknown Command!");
        };
    }

    private static int parseMark(String markString) throws ParserException {
        try {
            return Integer.parseInt(markString);
        } catch (NumberFormatException e) {
            throw new ParserException("Saved mark is not a valid integer!");
        }
    }

    private static ToDo parseTodo(String[] dataArray) throws ParserException {
        if (dataArray.length < 3) {
            throw new ParserException("Saved todo is missing either description or mark!");
        }
        return new ToDo(dataArray[2], parseMark(dataArray[1]));
    }

    private static Deadline parseDeadline(String[] dataArray) throws ParserException {
        if (dataArray.length < 4) {
            throw new ParserException("Saved deadline is missing either description, mark, or datetime!");
        }
        try {
            return new Deadline(dataArray[2],
                    stringToDateTime(dataArray[3]),
                    parseMark(dataArray[1]));

        } catch (DateTimeParseException e){
            throw new ParserException("Saved date time is in invalid format.");
        }
    }

    private static Event parseEvent(String[] dataArray) throws ParserException {
        if (dataArray.length < 5) {
            throw new ParserException("Saved event is missing either description, mark, or datetime!");
        }
        try {
            return new Event(dataArray[2],
                    stringToDateTime(dataArray[3]),
                    stringToDateTime(dataArray[4]),
                    parseMark(dataArray[1]));
        } catch (DateTimeParseException e){
            throw new ParserException("Saved date time is in invalid format.");
        }
    }

    private static CommandType parseKeyCommand(String keyCommand) throws ParserException {
        try {
            return CommandType.valueOf(keyCommand.toUpperCase());
        } catch (IllegalArgumentException iae) {
            throw new ParserException("Oops!!! I'm sorry, but I don't know what that means :<");
        }
    }


    private static String getArgumentOrThrow(String[] commandInfo, CommandType keyCommand) throws ParserException {
        if (commandInfo.length == 1) {
            throw new ParserException(ARG_REQUIRED_ERROR.get(keyCommand));
        }
        return commandInfo[1];
    }

    private static MarkCommand parseMarkCommand(String commandData) throws ParserException {
        try {
            int markNum = Integer.parseInt(commandData);
            return new MarkCommand(markNum);
        } catch (NumberFormatException nfe) {
            throw new ParserException("Oops! Mark command requires an integer index number!");
        }
    }

    private static UnmarkCommand parseUnmarkCommand(String commandData) throws ParserException {
        try {
            int markNum = Integer.parseInt(commandData);
            return new UnmarkCommand(markNum);
        } catch (NumberFormatException nfe) {
            throw new ParserException("Oops! Unmark requires an integer index number!");
        }
    }

    private static DeleteCommand parseDeleteCommand(String commandData) throws ParserException {
        // Yield new command and throw exception if input after space is not integer
        try {
            int deleteNum = Integer.parseInt(commandData);
            return new DeleteCommand(deleteNum);
        } catch (NumberFormatException nfe) {
            throw new ParserException(
                    "Oops! Delete requires an integer index number!");
        }
    }

    private static EventCommand parseEventCommand(String commandData) throws ParserException {
        String[] taskInfoList = commandData.split(" /from | /to ");

        // Throw exception if user inputs insufficient information
        if (taskInfoList.length < 3) {
            throw new ParserException(
                    "OOPS! An event requires a description, /from and /to timeframe!");
        } else {
            String description = taskInfoList[0];
            String fromString = taskInfoList[1];
            String toString = taskInfoList[2];

            LocalDateTime fromDateTime = parseDateTime(fromString);
            LocalDateTime toDateTime = parseDateTime(toString);

            return new EventCommand(description, fromDateTime, toDateTime);
        }
    }

    private static DeadlineCommand parseDeadlineCommand(String commandData) throws ParserException {
        String[] taskInfoList = commandData.split(" /by ");

        // Throw exception if user inputs insufficient information
        if (taskInfoList.length < 2) {
            throw new ParserException(
                    "OOPS! A deadline requires a description and /by deadline!");
        } else {
            // Separates the appropriate data from info
            String description = taskInfoList[0];
            String byString = taskInfoList[1];

            LocalDateTime byDateTime = parseDateTime(byString);
            return new DeadlineCommand(description, byDateTime);
        }
    }

    private static LocalDateTime parseDateTime(String dtString) throws ParserException{
        try {
            return LocalDateTime.parse(
                    dtString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        } catch (DateTimeParseException dtpe) {
            throw new ParserException(
                    "Date time is in invalid format! Please use dd/MM/yyyy HH:mm format!");
        }
    }

    /**
     * Reads from file or user input and converts into LocalDateTime object.
     *
     * @param dateTime The string read from file or user input.
     * @return LocalDateTime for further processing.
     */
    private static LocalDateTime stringToDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return LocalDateTime.parse(dateTime, formatter);
    }

}


