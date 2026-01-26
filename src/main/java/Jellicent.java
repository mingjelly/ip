import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;
import java.util.ArrayList;



public class Jellicent {
    public enum CommandType {
        LIST,
        BYE,
        MARK,
        UNMARK,
        TODO,
        DEADLINE,
        EVENT,
        DELETE
    }

    private static void saveListDataIntoFile(String filePath, ArrayList<Task> tasks) throws IOException {
        // Assume that the tasks are
        File file = new File(filePath);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (FileWriter fileWriter = new FileWriter(file)) {
            for (int i = 0; i < tasks.size(); i++) {
                fileWriter.write(tasks.get(i).toFileString() + "\n");
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
            switch (dataArray[0]) {
                case "T": task = new ToDo(dataArray[2]); break;
                case "D": task = new Deadline(dataArray[2], dataArray[3]); break;
                case "E": task = new Event(dataArray[2], dataArray[3], dataArray[4]);
                default: throw new IllegalArgumentException("Unknown Task Type: " + dataArray[0]);
            }
        }
        catch (ArrayIndexOutOfBoundsException aiofbe) {
            throw new IllegalArgumentException("Saved data is in the wrong format!");
        }
        if ("1".equals(dataArray[1])) {
            task.markAsDone();
        }
        return task;
    }

    private static ArrayList<Task> loadFileDataIntoList(String filePath) {
        File file = new File(filePath);
        ArrayList<Task> tasks = new ArrayList<>();
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

    private static void addTask(ArrayList<Task> tasks, Task task) {
        tasks.add(task);
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + tasks.size() + (tasks.size() == 1 ? " task" : " tasks") + " in the list.");
    }

    public static void main(String[] args) {

        String filePath;
        if (args.length > 0) {
            filePath = args[0];
        } else {
            filePath = "data/tasks.txt";
        }

        ArrayList<Task> tasks = loadFileDataIntoList(filePath);

        System.out.println("----------------------------------------------------------------------");
        System.out.println("Hello from Jellicent \nWhat can I do for you?");
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------------------------------------------------------");

        CommandType keyCommand;

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            String[] commands = command.split(" ", 2);
            try {
                System.out.println("----------------------------------------------------------------------");
                try {
                    keyCommand = CommandType.valueOf(commands[0].toUpperCase());
                } catch (IllegalArgumentException iae){
                    throw new JellicentException("Oops!!! I'm sorry, but I don't know what that means :<");
                }
                switch (keyCommand) {
                    case BYE: {
                        System.out.println("Bye! Hope to see you again!");
                        System.out.println("----------------------------------------------------------------------");
                        return;
                    }
                    case LIST: {
                        System.out.println("Here are the tasks in your list:");
                        for (int i = 0; i < tasks.size(); i++) {
                            Task currTask = tasks.get(i);
                            System.out.println(i+1 + ". " + currTask);
                        }
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
                                Task markedTask = tasks.get(markNum-1);
                                markedTask.markAsDone();
                                System.out.println("Nice! I've marked this task as done:");
                                System.out.println(markedTask);
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
                                Task markedTask = tasks.get(markNum - 1);
                                markedTask.markAsUndone();
                                System.out.println("Ok. I have marked this task as not done yet:");
                                System.out.println(markedTask);
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
                                Task deletedTask = tasks.remove(deleteNum - 1);
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
                            addTask(tasks, newTask);
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
                                Task newTask = new Deadline(taskInfoList[0], taskInfoList[1]);
                                tasks.add(newTask);
                                addTask(tasks, newTask);
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
                                Task newTask = new Event(taskInfoList[0], taskInfoList[1], taskInfoList[2]);
                                addTask(tasks, newTask);
                            }
                        }
                        break;
                    }
                    default: {
                        throw new JellicentException("OOPS! I'm sorry, but I don't know what that means :<");
                    }
                }
                saveListDataIntoFile(filePath, tasks);

            } catch (JellicentException | IOException je){
                System.out.println(je.getMessage());
            }
            System.out.println("----------------------------------------------------------------------");

        }


    }


}
