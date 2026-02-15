package jellicent.parser;

import jellicent.command.*;
import jellicent.entry.task.TaskList;
import jellicent.entry.task.ToDo;
import jellicent.entry.task.Deadline;
import jellicent.entry.task.Event;
import jellicent.entry.task.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Provides parsing utilities for the Jellicent application.
 *
 * <p>
 * The {@code Parser} class is responsible for converting raw input strings
 * or saved file data into {@link Task} objects or {@link Command} objects
 * that the application can execute. It handles validation and throws
 * {@link ParserException} for invalid formats or missing information.
 * </p>
 */
public class Parser {

    /**
     * Maps commands to the corresponding argument-required error messages.
     */
    private static final Map<CommandType, String> ARG_REQUIRED_ERROR;

    private static final int SAVED_INDEX_TYPE = 0;
    private static final int SAVED_INDEX_MARK = 1;
    private static final int SAVED_INDEX_DESCRIPTION = 2;
    private static final int SAVED_INDEX_DATETIME1 = 3;
    private static final int SAVED_INDEX_DATETIME2 = 4;
    private static final int SAVED_MIN_LENGTH_TODO = 3;
    private static final int SAVED_MIN_LENGTH_DEADLINE = 4;
    private static final int SAVED_MIN_LENGTH_EVENT = 5;
    private static final int SAVED_MIN_DATA_LENGTH = 2;

    private static final int INPUT_INDEX_KEYCOMMAND = 0;
    private static final int INPUT_INDEX_DESCRIPTION = 0; // exclude keycommand
    private static final int INPUT_INDEX_DATE1 = 1; // exclude keycommand
    private static final int INPUT_INDEX_DATE2 = 2; // exclude keycommand
    private static final int INPUT_MIN_LENGTH_DEADLINE = 2; // description + /by
    private static final int INPUT_MIN_LENGTH_EVENT = 3; // description + /from + /to
    private static final int INPUT_MAX_COMMAND_SPLIT = 2;
    private static final int ARGUMENT_INDEX = 1;

    private static final String REGEX_DELIMITER_DEADLINE = " /by ";
    private static final String REGEX_DELIMITER_EVENT = " /from | /to ";
    private static final String REGEX_DELIMITER_KEYCOMMAND_ARG = " ";
    private static final String REGEX_DELIMITER_SAVED_ENTRY = "\\|";

    private static final DateTimeFormatter INPUT_DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static final DateTimeFormatter SAVED_DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    static {
        ARG_REQUIRED_ERROR = Map.of(
                CommandType.MARK, "OOPS! Mark command requires an integer after",
                CommandType.UNMARK, "OOPS! Unmark command requires an integer after",
                CommandType.DELETE, "OOPS! Delete command requires an integer after",
                CommandType.TODO, "OOPS! The description of a todo cannot be empty.",
                CommandType.EVENT, "OOPS! An event requires a description, /from and /to timeframe!",
                CommandType.DEADLINE, "OOPS! A deadline requires a description and /by deadline!",
                CommandType.FIND, "OOPS! Find command requires a search word!",
                CommandType.VISIT, "Oops! Visit command requires a name!"
        );
    }

    /**
     * Converts loaded data into tasks.
     *
     * @param strings ArrayList of tasks' data from saved file.
     * @return Initialised TaskList at the start of program.
     * @throws ParserException for incorrect text format from file and task types from text file
     */
    public static TaskList stringsIntoTasks(ArrayList<String> strings) throws ParserException {
        assert strings != null : "Strings arraylist should not be null";

        TaskList taskList = new TaskList();

        for (String taskString : strings) {
            Task task = parseTaskLine(taskString);
            taskList.add(task);
        }
        return taskList;
    }

    /**
     * Converts a single input line into a command during program execution.
     *
     * @param string user input to tell the program what to do.
     * @return Executable Command for program to perform various actions.
     * @throws ParserException for any parsing issues within the code.
     */
    public static Command userInputIntoCommand(String string) throws ParserException {
        assert string != null : "String should not be null";

        // Split the user input into key command and additional info
        String[] commandInfo = string.split(REGEX_DELIMITER_KEYCOMMAND_ARG, INPUT_MAX_COMMAND_SPLIT);
        CommandType keyCommand = parseKeyCommand(commandInfo[INPUT_INDEX_KEYCOMMAND]);
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
            case VISIT -> new VisitCommand(getArgumentOrThrow(commandInfo, keyCommand));
            case PLACES -> new PlacesCommand();
            default -> throw new ParserException("Unknown Command!");
        };
    }

    private static Task parseTaskLine(String taskString) throws ParserException {
        String[] dataArray = tokenizeSavedEntry(taskString);
        validateSavedDataPrimary(dataArray);
        return parseTaskByType(dataArray);

    }

    private static String[] tokenizeSavedEntry(String taskString) {
        return taskString.trim().split(REGEX_DELIMITER_SAVED_ENTRY);
    }

    private static void validateSavedDataPrimary(String[] dataArray) throws ParserException {
        if (dataArray.length < SAVED_MIN_DATA_LENGTH) {
            throw new ParserException("Tasks are saved in incorrect format!");
        }
    }

    private static void validateMinLength(String[] dataArray, int length, String msg) throws ParserException {
        if (dataArray.length < length) {
            throw new ParserException(msg);
        }
    }

    private static Task parseTaskByType(String[] dataArray) throws ParserException {
        return switch (dataArray[SAVED_INDEX_TYPE]) {
            case "T" -> parseTodo(dataArray);
            case "D" -> parseDeadline(dataArray);
            case "E" -> parseEvent(dataArray);
            default -> throw new ParserException("Unknown Task Type: " + dataArray[SAVED_INDEX_TYPE]);
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
        validateMinLength(dataArray,
                SAVED_MIN_LENGTH_TODO,
                "Saved todo is missing either description or mark!");
        String description = dataArray[SAVED_INDEX_DESCRIPTION];
        int mark = parseMark(dataArray[SAVED_INDEX_MARK]);
        return new ToDo(description, mark);
    }

    private static Deadline parseDeadline(String[] dataArray) throws ParserException {
        validateMinLength(dataArray,
                SAVED_MIN_LENGTH_DEADLINE,
                "Saved deadline is missing either description, mark, or datetime!");
        String description = dataArray[SAVED_INDEX_DESCRIPTION];
        LocalDateTime byDate = parseSavedDateTime(dataArray[SAVED_INDEX_DATETIME1]);
        int mark = parseMark(dataArray[SAVED_INDEX_MARK]);
        return new Deadline(description, byDate, mark);
    }

    private static Event parseEvent(String[] dataArray) throws ParserException {
        validateMinLength(dataArray,
                SAVED_MIN_LENGTH_EVENT,
                "Saved event is missing either description, mark, or datetime!");
        String description = dataArray[SAVED_INDEX_DESCRIPTION];
        LocalDateTime fromDate = parseSavedDateTime(dataArray[SAVED_INDEX_DATETIME1]);
        LocalDateTime toDate = parseSavedDateTime(dataArray[SAVED_INDEX_DATETIME2]);
        int mark = parseMark(dataArray[SAVED_INDEX_MARK]);
        return new Event(description, fromDate, toDate, mark);
    }

    private static CommandType parseKeyCommand(String keyCommand) throws ParserException {
        try {
            return CommandType.valueOf(keyCommand.toUpperCase());
        } catch (IllegalArgumentException iae) {
            throw new ParserException("Oops!!! I'm sorry, but I don't know what that means :<");
        }
    }


    private static String getArgumentOrThrow(String[] commandInfo, CommandType keyCommand) throws ParserException {
        if (commandInfo.length <= ARGUMENT_INDEX) {
            throw new ParserException(ARG_REQUIRED_ERROR.get(keyCommand));
        }
        return commandInfo[ARGUMENT_INDEX];
    }

    private static MarkCommand parseMarkCommand(String commandData) throws ParserException {
        int markNum = parseIndex(commandData, "Oops! Mark command requires an integer index number!");
        return new MarkCommand(markNum);
    }

    private static UnmarkCommand parseUnmarkCommand(String commandData) throws ParserException {
        int markNum = parseIndex(commandData,"Oops! Unmark requires an integer index number!");
        return new UnmarkCommand(markNum);
    }

    private static DeleteCommand parseDeleteCommand(String commandData) throws ParserException {
        int deleteNum = parseIndex(commandData, "Oops! Delete requires an integer index number!");
        return new DeleteCommand(deleteNum);
    }

    private static int parseIndex(String input, String errorMsg ) throws ParserException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            throw new ParserException(errorMsg);
        }
    }

    private static EventCommand parseEventCommand(String commandData) throws ParserException {
        String[] taskInfoList = commandData.split(REGEX_DELIMITER_EVENT);

        // Throw exception if user inputs insufficient information
        validateMinLength(taskInfoList,
                INPUT_MIN_LENGTH_EVENT,
                "OOPS! An event requires a description, /from and /to timeframe!");

        String description = taskInfoList[INPUT_INDEX_DESCRIPTION];
        String fromString = taskInfoList[INPUT_INDEX_DATE1];
        String toString = taskInfoList[INPUT_INDEX_DATE2];

        LocalDateTime fromDateTime = parseInputDateTime(fromString);
        LocalDateTime toDateTime = parseInputDateTime(toString);

        return new EventCommand(description, fromDateTime, toDateTime);
    }

    private static DeadlineCommand parseDeadlineCommand(String commandData) throws ParserException {
        String[] taskInfoList = commandData.split(REGEX_DELIMITER_DEADLINE);

        validateMinLength(taskInfoList,
                INPUT_MIN_LENGTH_DEADLINE,
                "OOPS! A deadline requires a description and /by deadline!");
        String description = taskInfoList[INPUT_INDEX_DESCRIPTION];
        String byString = taskInfoList[INPUT_INDEX_DATE1];

        LocalDateTime byDateTime = parseInputDateTime(byString);
        return new DeadlineCommand(description, byDateTime);
    }

    private static LocalDateTime parseInputDateTime(String dtString) throws ParserException {
        try {
            return LocalDateTime.parse(
                    dtString, INPUT_DATE_FORMAT);
        } catch (DateTimeParseException dtpe) {
            throw new ParserException(
                    "Date time is in invalid format! Please use dd/MM/yyyy HH:mm format!");
        }
    }

    private static LocalDateTime parseSavedDateTime(String dateTime) throws ParserException {
        try {
            return LocalDateTime.parse(dateTime, SAVED_DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new ParserException("Saved date time is in invalid format");
        }
    }
}
