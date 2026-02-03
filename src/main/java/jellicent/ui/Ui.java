package jellicent.ui;

import java.lang.StringBuilder;

import jellicent.task.Task;
import jellicent.task.TaskList;

public class Ui {

    /**
     * Returns greet message when program initialises.
     *
     * @return Formatted greeting message for display.
     */
    public String greetUser() {
        return "Hello from Jellicent\nWhat can I do for you?";
    }

    /**
     * Returns farewell message that occurs when program exits.
     *
     * @return Formatted farewell message for display.
     */
    public String farewellUser() {
        return "Bye! Hope to see you again!";
    }

    /**
     * Shows error interface when and error occurs.
     *
     * @param errMessage Error message input to be shown.
     * @return Formatted error message for display.
     */
    public String showError(String errMessage) {
        return errMessage;
    }

    /**
     * Returns list of all tasks that are currently stored in tasks.
     *
     * @param tasks Tasks to iterate through and list.
     * @return Formatted list message for display.
     */
    public String listTasks(TaskList tasks) {
        StringBuilder taskMsg = new StringBuilder();
        taskMsg.append("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task currTask = tasks.get(i);
            taskMsg.append("\n")
                    .append(i+1)
                    .append(". ")
                    .append(currTask);
        }
        return taskMsg.toString();
    }

    /**
     * Returns message for when a task has been marked done.
     *
     * @param task Task that was marked, that is used for the ui message.
     * @return Formatted mark message for display.
     */
    public String markDone(Task task) {
        return "Nice! I've marked this task as done:\n" + task;
    }

    /**
     * Returns message for when a task has been marked undone.
     *
     * @param task Task that was marked undone, that is used for the ui message.
     * @return Formatted unmark message for display.
     */
    public String markUndone(Task task) {
        return "Ok. I have marked this task as not done yet:\n" + task;
    }

    /**
     * Helper method that returns the number of tasks left.
     *
     * @param tasks TaskList that is currently used in the program.
     * @return StringBuilder that shows number of tasks remaining.
     */
    private StringBuilder tasksLeft(TaskList tasks) {
        StringBuilder resString = new StringBuilder();
        return resString.append("Now you have ")
                .append(tasks.size())
                .append(tasks.size() == 1 ? " task" : " tasks")
                .append(" in the list.");
    }

    /**
     * Returns formatted message when a task is added.
     *
     * @param tasks TaskList that is currently used in the program.
     * @param task Task that was added to tasks.
     * @return Formatted add message for display.
     */
    public String addTask(TaskList tasks, Task task) {
        return "Got it. I've added this task:" + "\n"
                + task + "\n" + tasksLeft(tasks);
    }

    /**
     * Returns formatted message when a task is deleted.
     *
     * @param tasks TaskList that is currently used in the program.
     * @param task Task that was deleted from tasks.
     * @return Formatted delete message for display.
     */
    public String deleteTask(TaskList tasks, Task task) {
        return "Noted, I have removed this task:\n" + task
                + "\n" + tasksLeft(tasks);
    }

    /**
     * Returns formatted message to find all matching tasks.
     *
     * @param matchingTasks TaskList of tasks that match the keyword.
     * @return Formatted matching message for display.
     */
    public String matchingTasks(TaskList matchingTasks) {
        StringBuilder matchString = new StringBuilder();
        if (matchingTasks.size() == 0) {
            matchString.append("There are no matching tasks in your lists.");
        } else {
            matchString.append("Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                Task currTask = matchingTasks.get(i);
                matchString.append("\n")
                        .append(i+1)
                        .append(". ")
                        .append(currTask);
            }
        }
        return matchString.toString();
    }

}
