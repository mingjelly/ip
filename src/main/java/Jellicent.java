import java.util.Scanner;

public class Jellicent {
    public static void main(String[] args) {
        String name = "Jellicent";
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Hello from " + name + "\nWhat can I do for you?");
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------------------------------------------------------");

        String command = scanner.nextLine();
        Task[] tasks = new Task[100];
        int taskCounter = 0;

        while (!command.equals("bye")) {
            String[] commands = command.split(" ", 2);
            String keyCommand = commands[0];
            System.out.println("----------------------------------------------------------------------");
            switch (keyCommand) {
                case "list": {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < taskCounter; i++) {
                        Task currTask = tasks[i];
                        System.out.println(i+1 + ". " + currTask);
                    }
                    break;
                }
                case "mark": {
                    // Assumes that what comes after is a number
                    int markNum = Integer.parseInt(commands[1]);
                    Task markedTask = tasks[markNum - 1];
                    markedTask.markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(markedTask);
                    break;
                }
                case "unmark": {
                    int markNum = Integer.parseInt(commands[1]);
                    Task markedTask = tasks[markNum - 1];
                    markedTask.markAsUndone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(markedTask);
                    break;
                }
                case "todo": {
                    String taskInfo = commands[1];
                    Task newTask = new ToDo(taskInfo);
                    tasks[taskCounter] = newTask;
                    System.out.println("Got it. I've added this task: ");
                    System.out.println(newTask);
                    taskCounter++;
                    if (taskCounter == 1) {
                        System.out.println("Now you have " + taskCounter + " task in the list.");
                    } else {
                        System.out.println("Now you have " + taskCounter + " tasks in the list.");
                    }
                    break;
                }
                case "deadline": {
                    String taskInfo = commands[1];
                    String[] taskInfoList = taskInfo.split(" /by ");
                    Task newTask = new Deadline(taskInfoList[0], taskInfoList[1]);
                    tasks[taskCounter] = newTask;
                    System.out.println("Got it. I've added this task: ");
                    System.out.println(newTask);
                    taskCounter++;
                    if (taskCounter == 1) {
                        System.out.println("Now you have " + taskCounter + " task in the list.");
                    } else {
                        System.out.println("Now you have " + taskCounter + " tasks in the list.");
                    }                    break;
                }
                case "event": {
                    String taskInfo = commands[1];
                    String[] taskInfoList = taskInfo.split(" /from | /to ");
                    Task newTask = new Event(taskInfoList[0], taskInfoList[1], taskInfoList[2]);
                    tasks[taskCounter] = newTask;
                    System.out.println("Got it. I've added this task: ");
                    System.out.println(newTask);
                    taskCounter++;
                    if (taskCounter == 1) {
                        System.out.println("Now you have " + taskCounter + " task in the list.");
                    } else {
                        System.out.println("Now you have " + taskCounter + " tasks in the list.");
                    }
                    break;
                }
            }
            System.out.println("----------------------------------------------------------------------");
            command = scanner.nextLine();

        }
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Bye! Hope to see you again!");
        System.out.println("----------------------------------------------------------------------");

    }


}
