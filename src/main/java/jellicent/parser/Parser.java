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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Parser {

    /**
     * Reads from file or user input and converts into LocalDateTime object.
     *
     * @param dateTime The string read from file or user input.
     *
     * @return LocalDateTime for further processing.
     */
    private static LocalDateTime stringToDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return LocalDateTime.parse(dateTime, formatter);
    }

    /**
     * Converts loaded data into tasks.
     *
     * @param strings ArrayList of tasks' data from saved file.
     *
     * @return Initialised TaskList at the start of program.
     */
    public static TaskList stringsIntoTasks(ArrayList<String> strings) throws ParserException {
        TaskList taskList = new TaskList();

        // Iterate through all input
        for (String taskString : strings) {
            String[] dataArray = taskString.split("\\|");

            // Throw exception if only 1 word is read
            if (dataArray.length == 1) {
                throw new ParserException("Tasks are saved in incorrect format!");
            }

            Task task;
            try {
                // Create command and funnel data according to the respective inputs
                int mark = Integer.parseInt(dataArray[1]);
                task = switch (dataArray[0]) {
                    case "T" -> new ToDo(dataArray[2], mark);
                    case "D" -> new Deadline(dataArray[2], stringToDateTime(dataArray[3]), mark);
                    case "E" -> new Event(
                            dataArray[2],
                            stringToDateTime(dataArray[3]),
                            stringToDateTime(dataArray[4]),
                            mark
                    );
                    default -> throw new ParserException("Unknown Task Type: " + dataArray[0]);
                };
            } catch (ArrayIndexOutOfBoundsException | DateTimeParseException e) {
                throw new ParserException("Saved data is in the wrong format!");
            }
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
        // Split the user input into key command and additional info
        String[] commandInfo = string.split(" ", 2);

        // Checks whether the keyCommand falls under recognised command for CommandType enum
        CommandType keyCommand;
        try {
            keyCommand = CommandType.valueOf(commandInfo[0].toUpperCase());
        } catch (IllegalArgumentException iae) {
            throw new ParserException("Oops!!! I'm sorry, but I don't know what that means :<");
        }

        return switch (keyCommand) {
            case BYE -> new ByeCommand();
            case LIST -> new ListCommand();
            case MARK -> {
                // Throw exception if user only inputs 1 word
                if (commandInfo.length == 1) {
                    throw new ParserException("OOPS! Mark command requires an integer after");
                }

                // Yield new command and throw exception if input after space is not integer
                try {
                    int markNum = Integer.parseInt(commandInfo[1]);
                    yield new MarkCommand(markNum);
                } catch (NumberFormatException nfe) {
                    throw new ParserException("Oops! Mark command requires an integer index number!");
                }

            }
            case UNMARK -> {
                // Throw exception if user only inputs 1 word
                if (commandInfo.length == 1) {
                    throw new ParserException("OOPS! Unmark command requires an integer after");
                }

                // Yield new command and throw exception if input after space is not integer
                try {
                    int markNum = Integer.parseInt(commandInfo[1]);
                    yield new UnmarkCommand(markNum);
                } catch (NumberFormatException nfe) {
                    throw new ParserException("Oops! Unmark requires an integer index number!");
                }
            }
            case DELETE -> {
                // Throw exception if user only inputs 1 word
                if (commandInfo.length == 1) {
                    throw new ParserException("OOPS! Delete command requires an integer after");
                }

                // Yield new command and throw exception if input after space is not integer
                try {
                    int deleteNum = Integer.parseInt(commandInfo[1]);
                    yield new DeleteCommand(deleteNum);
                } catch (NumberFormatException nfe) {
                    throw new ParserException(
                            "Oops! Delete requires an integer index number!");
                }
            }
            case TODO -> {
                // Throw exception if user only inputs 1 word
                if (commandInfo.length == 1) {
                    throw new ParserException(
                            "OOPS! The description of a todo cannot be empty.");
                }
                yield new TodoCommand(commandInfo[1]);
            }
            case EVENT -> {
                String[] taskInfoList = commandInfo[1].split(" /from | /to ");

                // Throw exception if user inputs insufficient information
                if (taskInfoList.length < 3) {
                    throw new ParserException(
                            "OOPS! An event requires a description, /from and /to timeframe!");
                } else {
                    // Separates the appropriate data from info
                    String description = taskInfoList[0];
                    String fromString = taskInfoList[1];
                    String toString = taskInfoList[2];

                    LocalDateTime fromDateTime;
                    LocalDateTime toDateTime;

                    // Converts DateTime to correct format for initialisation
                    try {
                        fromDateTime = LocalDateTime.parse(
                                fromString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                        toDateTime = LocalDateTime.parse(
                                toString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                    } catch (DateTimeParseException dtpe) {
                        throw new ParserException(
                                "Date time is in invalid format! Please use dd/MM/yyyy HH:mm format!");
                    }
                    yield new EventCommand(description, fromDateTime, toDateTime);
                }
            }
            case DEADLINE -> {
                String[] taskInfoList = commandInfo[1].split(" /by ");

                // Throw exception if user inputs insufficient information
                if (taskInfoList.length == 1) {
                    throw new ParserException(
                            "OOPS! A deadline requires a description and /by deadline!");
                } else {
                    // Separates the appropriate data from info
                    String description = taskInfoList[0];
                    String byString = taskInfoList[1];

                    LocalDateTime byDateTime;

                    // Converts DateTime to correct format for initialisation
                    try {
                        byDateTime = LocalDateTime.parse(
                                byString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                    } catch (DateTimeParseException dtpe) {
                        throw new ParserException(
                                "Date time is in invalid format! Please use dd/MM/yyyy HH:mm format!");
                    }
                    yield new DeadlineCommand(description, byDateTime);
                }
            }
            case FIND -> {
                if (commandInfo.length == 1) {
                    throw new ParserException("OOPS! Find command requires a search word!");
                }
                yield new FindCommand(commandInfo[1]);
            }
        };
    }
}
