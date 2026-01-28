package jellicent.ui;

import jellicent.task.Task;
import jellicent.task.TaskList;

public class Ui {
    public Ui() {
        this.greetUser();
    }

    /**
     * Buffer line which will be used for most of ui printing.
     */
    private void printLine() {
        System.out.println("----------------------------------------------------------------------");
    }

    /**
     * Shows greeting interface when program initialises.
     */
    public void greetUser() {
        printLine();
        System.out.println("Hello from Jellicent\nWhat can I do for you?");
        printLine();
    }

    /**
     * Shows farewell interface that occurs when program exits.
     */
    public void farewellUser() {
        printLine();
        System.out.println("Bye! Hope to see you again!");
        printLine();
    }

    /**
     * Shows error interface when and error occurs.
     */
    public void showError(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }

    /**
     * Lists out all tasks that are currently stored in tasks.
     *
     * @param tasks Tasks to iterate through and list.
     */
    public void listTasks(TaskList tasks) {
        printLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task currTask = tasks.get(i);
            System.out.println(i+1 + ". " + currTask);
        }
        printLine();
    }

    /**
     * Shows interface for when a task has been marked done.
     *
     * @param task Task that was marked, that is used for the ui message.
     */
    public void markDone(Task task) {
        printLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
        printLine();
    }

    /**
     * Shows interface for when a task has been marked undone.
     *
     * @param task Task that was marked undone, that is used for the ui message.
     */
    public void markUndone(Task task) {
        printLine();
        System.out.println("Ok. I have marked this task as not done yet:");
        System.out.println(task);
        printLine();
    }

    /**
     * Shows the number of tasks left.
     *
     * @param tasks TaskList that is currently used in the program.
     */
    private void tasksLeft(TaskList tasks) {
        System.out.println("Now you have " + tasks.size()
                + (tasks.size() == 1 ? " task" : " tasks") + " in the list.");

    }

    /**
     * Shows the ui when a task is added.
     *
     * @param tasks TaskList that is currently used in the program.
     * @param task Task that was added to tasks.
     */
    public void addTask(TaskList tasks, Task task) {
        printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        tasksLeft(tasks);
        printLine();
    }
    /**
     * Shows the ui when a task is deleted.
     *
     * @param tasks TaskList that is currently used in the program.
     * @param task Task that was deleted from tasks.
     */
    public void deleteTask(TaskList tasks, Task task) {
        printLine();
        System.out.println("Noted, I have removed this task:");
        System.out.println(task);
        tasksLeft(tasks);
        printLine();
    }

    public void matchingTasks(TaskList matchingTasks) {
        printLine();
        if (matchingTasks.size() == 0) {
            System.out.println("There are no matching tasks in your lists.");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                Task currTask = matchingTasks.get(i);
                System.out.println(i+1 + ". " + currTask);
            }
        }
        printLine();
    }

}
