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

    public static void main(String[] args) {
        String name = "Jellicent";
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Hello from " + name + "\nWhat can I do for you?");
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------------------------------------------------------");

        ArrayList<Task> tasks = new ArrayList<>();

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
                                // 1 Not Integer, 2 Index out of bounds
                                int deleteNum = Integer.parseInt(commands[1]);
                                Task deletedTask = tasks.remove(deleteNum - 1);
                                System.out.println("Noted, I have removed this task:");
                                System.out.println(deletedTask);
                                if (tasks.size() == 1) {
                                    System.out.println("Now you have " + tasks.size() + " task in the list.");
                                } else {
                                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                                }
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
                            System.out.println("Got it. I've added this task:");
                            System.out.println(newTask);
                            if (tasks.size() == 1) {
                                System.out.println("Now you have " + tasks.size() + " task in the list.");
                            } else {
                                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                            }
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
                                System.out.println("Got it. I've added this task:");
                                System.out.println(newTask);
                                if (tasks.size() == 1) {
                                    System.out.println("Now you have " + tasks.size() + " task in the list.");
                                } else {
                                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                                }
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
                                tasks.add(newTask);
                                System.out.println("Got it. I've added this task:");
                                System.out.println(newTask);
                                if (tasks.size() == 1) {
                                    System.out.println("Now you have " + tasks.size() + " task in the list.");
                                } else {
                                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                                }
                            }
                        }
                        break;
                    }
                    default: {
                        throw new JellicentException("OOPS! I'm sorry, but I don't know what that means :<");
                    }
                }


            } catch (JellicentException je){
                System.out.println(je.getMessage());
            }
            System.out.println("----------------------------------------------------------------------");

        }


    }


}
