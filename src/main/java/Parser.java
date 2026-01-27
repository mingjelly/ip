import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Parser {
    private static LocalDateTime stringToDateTime(String dateTime) {
        // Reads from file or input
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return LocalDateTime.parse(dateTime, formatter);
    }

    public static TaskList stringsIntoTasks(ArrayList<String> strings) throws ParserException {
            // Assumes that the string is stored in the form D|1|description|by
            TaskList taskList = new TaskList();

            for (String taskString:strings) {
                String[] dataArray = taskString.split("\\|");
                if (dataArray.length == 1) {
                    throw new ParserException("Tasks are saved in incorrect format!");
                }

                Task task;
                try {
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

    public static Command userInputIntoCommand(String string) throws ParserException {
        String[] commandInfo = string.split(" ", 2);

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
                if (commandInfo.length == 1) {
                    throw new ParserException("OOPS! Mark command requires an integer after");
                }
                try {
                    int markNum = Integer.parseInt(commandInfo[1]);
                    yield new MarkCommand(markNum);
                } catch (NumberFormatException nfe) {
                    throw new ParserException("Oops! Mark command requires an integer index number!");
                }

            } // Possible error: 1) not integer, 2) no commandInfo[1], or integer out of bounds?
            case UNMARK -> {
                if (commandInfo.length == 1) {
                    throw new ParserException("OOPS! Unmark command requires an integer after");
                }
                try {
                    int markNum = Integer.parseInt(commandInfo[1]);
                    yield new UnmarkCommand(markNum);
                } catch (NumberFormatException nfe) {
                    throw new ParserException("Oops! Unmark requires an integer index number!");
                }
            }
            case DELETE -> {
                if (commandInfo.length == 1) {
                    throw new ParserException("OOPS! Unmark command requires an integer after");
                }
                try {
                    int deleteNum = Integer.parseInt(commandInfo[1]);
                    yield new DeleteCommand(deleteNum);
                } catch (NumberFormatException nfe) {
                    throw new ParserException("Oops! Unmark requires an integer index number!");
                }
                // Execute might lead to different problems (e.g. int is outside the array...)
            }
            case TODO -> {
                if (commandInfo.length == 1) {
                    throw new ParserException("OOPS! Unmark command requires an integer after");
                }
                yield new TodoCommand(commandInfo[1]);
            } // Possible error, 1) No commandInfo
            case EVENT -> {
                String[] taskInfoList = commandInfo[1].split(" /from | /to ");
                if (taskInfoList.length < 3) {
                    throw new ParserException("OOPS! An event requires a description, /from and /to timeframe!");
                } else {
                    String description = taskInfoList[0];
                    String fromString = taskInfoList[1];
                    String toString = taskInfoList[2];

                    LocalDateTime fromDateTime;
                    LocalDateTime toDateTime;

                    try {
                        fromDateTime = LocalDateTime.parse(fromString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                        toDateTime = LocalDateTime.parse(toString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                    } catch (DateTimeParseException dtpe) {
                        throw new ParserException("Date time is in invalid format! Please use dd/MM/yyyy HH:mm format!");
                    }
                    yield new EventCommand(description, fromDateTime, toDateTime);
                }
            }
            case DEADLINE -> {
                String[] taskInfoList = commandInfo[1].split(" /by ");
                if (taskInfoList.length == 1) {
                    throw new ParserException("OOPS! A deadline requires a description and /by deadline!");
                } else {
                    String description = taskInfoList[0];
                    String byString = taskInfoList[1];

                    LocalDateTime byDateTime;

                    try {
                        byDateTime = LocalDateTime.parse(byString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                    } catch (DateTimeParseException dtpe) {
                        throw new ParserException("Date time is in invalid format! Please use dd/MM/yyyy HH:mm format!");
                    }
                    yield new DeadlineCommand(description, byDateTime);
                }
            }
        };
    }

}
