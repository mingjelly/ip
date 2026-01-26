import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Jellicent {
    private static void saveListDataIntoFile(String filePath, TaskList tasks) throws IOException {
        // Assume that the tasks are
        File file = new File(filePath);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (FileWriter fileWriter = new FileWriter(file)) {
            for (Task task: tasks) {
                fileWriter.write(task.toFileString() + "\n");
            }
        }
    }

    private static Task parseStringIntoTask(String taskString) throws IllegalArgumentException {
        // Assumes that the string is stored in the form D|1|description|by
        String[] dataArray = taskString.split("\\|");

        if (dataArray.length == 1) {
            throw new IllegalArgumentException("Tasks are saved in incorrect format!");
        }

        Task task;
        try {
            task = switch (dataArray[0]) {
                case "T" -> new ToDo(dataArray[2]);
                case "D" -> new Deadline(dataArray[2], dataArray[3]);
                case "E" -> new Event(dataArray[2], dataArray[3], dataArray[4]);
                default -> throw new IllegalArgumentException("Unknown Task Type: " + dataArray[0]);
            };
        }
        catch (ArrayIndexOutOfBoundsException | DateTimeParseException e) {
            throw new IllegalArgumentException("Saved data is in the wrong format!");
        }
        if ("1".equals(dataArray[1])) {
            task.markAsDone();
        }
        return task;
    }

    private static TaskList loadFileDataIntoList(String filePath) {
        File file = new File(filePath);
        TaskList tasks = new TaskList();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                Task task = parseStringIntoTask(data);
                tasks.add(task);
            }
            return tasks;
        }
        catch (FileNotFoundException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return tasks;
        }
    }



    public static void main(String[] args) {
        String filePath;
        if (args.length > 0) {
            filePath = args[0];
        } else {
            filePath = "data/tasks.txt";
        }

        TaskList tasks = loadFileDataIntoList(filePath);
        Ui ui = new Ui();

        ui.greetUser();
        Scanner scanner = new Scanner(System.in);

        CommandType keyCommand;

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            String[] commands = command.split(" ", 2);
            try {
                try {
                    keyCommand = CommandType.valueOf(commands[0].toUpperCase());
                } catch (IllegalArgumentException iae){
                    throw new JellicentException("Oops!!! I'm sorry, but I don't know what that means :<");
                }
                switch (keyCommand) {
                    case BYE: {
                        ui.farewellUser();
                        return;
                    }
                    case LIST: {
                        ui.listTasks(tasks);
                        break;
                    }
                    case MARK: {
                        // Assumes that what comes after is a number
                        if (commands.length == 1) {
                            throw new JellicentException("Oops! Mark requires an index number!");
                        } else {
                            try {
                                // 1 Not Integer, 2 Index out of bounds
                                int markNum = Integer.parseInt(commands[1]);
                                Task markedTask = tasks.markDone(markNum);
                                ui.markDone(markedTask);
                            } catch (NumberFormatException nfe) {
                                throw new JellicentException("Oops! Mark requires an integer index number!");
                            } catch (IndexOutOfBoundsException ioobe) {
                                throw new JellicentException("Oops! There are only " + tasks.size() + " tasks in the list.");
                            }
                        }

                        break;
                    }
                    case UNMARK: {
                        if (commands.length == 1) {
                            throw new JellicentException("Oops! Mark requires an index number!");
                        } else {
                            try {
                                // 1 Not Integer, 2 Index out of bounds
                                int markNum = Integer.parseInt(commands[1]);
                                Task markedTask = tasks.markUndone(markNum);
                                ui.markUndone(markedTask);
                            } catch (NumberFormatException nfe) {
                                throw new JellicentException("Oops! Unmark requires an integer index number!");
                            } catch (IndexOutOfBoundsException ioobe) {
                                throw new JellicentException("Oops! There are only " + tasks.size() + " tasks in the list.");
                            }
                        }
                        break;
                    }
                    case DELETE: {
                        if (commands.length == 1) {
                            throw new JellicentException("Oops! Delete requires an index number!");
                        } else {
                            try {
                                int deleteNum = Integer.parseInt(commands[1]);
                                Task deletedTask = tasks.remove(deleteNum);
                                System.out.println("Noted, I have removed this task:");
                                System.out.println(deletedTask);
                                System.out.println("Now you have " + tasks.size() + (tasks.size() == 1 ? " task" : " tasks") + " in the list.");
                            } catch (NumberFormatException nfe) {
                                throw new JellicentException("Oops! Delete requires an integer index number!");
                            } catch (IndexOutOfBoundsException ioobe) {
                                throw new JellicentException("Oops! There are only " + tasks.size() + " tasks in the list.");
                            }
                        }
                        break;

                    }
                    case TODO: {
                        if (commands.length == 1) {
                            throw new JellicentException("OOPS! The description of a todo cannot be empty.");
                        } else {
                            String taskInfo = commands[1];
                            Task newTask = new ToDo(taskInfo);
                            tasks.add(newTask);
                            ui.addTask(tasks, newTask);
                        }
                        break;
                    }
                    case DEADLINE: {
                        if (commands.length == 1) {
                            throw new JellicentException("OOPS! The description of a deadline cannot be empty!");
                        } else {
                            String taskInfo = commands[1];
                            String[] taskInfoList = taskInfo.split(" /by ");
                            if (taskInfoList.length == 1) {
                                throw new JellicentException("OOPS! A deadline requires a description and /by deadline!");
                            } else {
                                String description = taskInfoList[0];
                                String byString = taskInfoList[1];

                                LocalDateTime byDateTime;

                                try {
                                    byDateTime = LocalDateTime.parse(byString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                                }
                                catch (DateTimeParseException dtpe) {
                                    throw new IllegalArgumentException("Date time is in invalid format! Please use dd/MM/yyyy HH:mm format!");
                                }
                                Task newTask = new Deadline(description, byDateTime);
                                tasks.add(newTask);
                                ui.addTask(tasks, newTask);
                            }
                        }
                        break;
                    }
                    case EVENT: {
                        if (commands.length == 1) {
                            throw new JellicentException("OOPS! The description of an event cannot be empty!");
                        } else {
                            String taskInfo = commands[1];
                            String[] taskInfoList = taskInfo.split(" /from | /to ");
                            if (taskInfoList.length < 3) {
                                throw new JellicentException("OOPS! An event requires a description, /from and /to timeframe!");
                            } else {
                                String description = taskInfoList[0];
                                String fromString = taskInfoList[1];
                                String toString = taskInfoList[2];

                                LocalDateTime fromDateTime;
                                LocalDateTime toDateTime;

                                try {
                                    fromDateTime = LocalDateTime.parse(fromString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                                    toDateTime = LocalDateTime.parse(toString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                                }
                                catch (DateTimeParseException dtpe) {
                                    throw new IllegalArgumentException("Date time is in invalid format! Please use dd/MM/yyyy HH:mm format!");
                                }

                                Task newTask = new Event(description, fromDateTime, toDateTime);
                                tasks.add(newTask);
                                ui.addTask(tasks, newTask);
                            }
                        }
                        break;
                    }
                    default: {
                        throw new JellicentException("OOPS! I'm sorry, but I don't know what that means :<");
                    }
                }
                saveListDataIntoFile(filePath, tasks);

            } catch (JellicentException | IOException | IllegalArgumentException je){
                ui.showError(je.getMessage());
            }
        }


    }


}
