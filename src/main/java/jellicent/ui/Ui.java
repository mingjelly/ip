package jellicent.ui;

import java.lang.StringBuilder;

import jellicent.entry.Place;
import jellicent.entry.PlaceList;
import jellicent.entry.task.Task;
import jellicent.entry.task.TaskList;

/**
 * Handles all user interface interactions in Jellicent.
 *
 * This class is responsible for generating formatted messages to display
 * to the user, including greetings, farewells, task listings, and
 * notifications about task operations such as adding, deleting, marking,
 * and finding tasks. It also formats messages when no tasks or matching
 * tasks are found.
 *
 * All display strings are formatted consistently and use constants for
 * common values such as newlines, task separators, and indexing offsets.
 */
public class Ui {
    private static final int NO_TASKS = 0;
    private static final int DISPLAY_INDEX_OFFSET = 1;
    private static final int SINGULAR_TASK = 1;
    private static final String NEWLINE = "\n";
    private static final String TASK_SEPARATOR = ". ";

    /**
     * Returns greet message when program initialises.
     *
     * @return Formatted greeting message for display.
     */
    public String greetUser() {
        return "Hello from Jellicent" +
        NEWLINE + "What can I do for you?";
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
        assert tasks != null : "TaskList should not be null";
        assert tasks.size() > 0 :  "There must me tasks in the list";

        StringBuilder taskMsg = new StringBuilder();
        taskMsg.append("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task currTask = tasks.get(i);
            taskMsg.append(NEWLINE)
                    .append(i + DISPLAY_INDEX_OFFSET)
                    .append(TASK_SEPARATOR)
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
        assert task != null : "Task should not be null";

        return "Nice! I've marked this task as done:\n" + task;
    }

    /**
     * Returns message for when a task has been marked undone.
     *
     * @param task Task that was marked undone, that is used for the ui message.
     * @return Formatted unmark message for display.
     */
    public String markUndone(Task task) {
        assert task != null : "Task should not be null";

        return "Ok. I have marked this task as not done yet:\n" + task;
    }

    /**
     * Helper method that returns the number of tasks left.
     *
     * @param tasks TaskList that is currently used in the program.
     * @return StringBuilder that shows number of tasks remaining.
     */
    private StringBuilder tasksLeft(TaskList tasks) {
        assert tasks != null : "TaskList should not be null";

        StringBuilder resString = new StringBuilder();
        return resString.append("Now you have ")
                .append(tasks.size())
                .append(tasks.size() == SINGULAR_TASK ? " task" : " tasks")
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
        assert tasks != null : "TaskList should not be null";
        assert task != null : "Task should not be null";

        return "Got it. I've added this task:" + NEWLINE
                + task + NEWLINE + tasksLeft(tasks);
    }

    /**
     * Returns formatted message when a task is deleted.
     *
     * @param tasks TaskList that is currently used in the program.
     * @param task Task that was deleted from tasks.
     * @return Formatted delete message for display.
     */
    public String deleteTask(TaskList tasks, Task task) {
        assert tasks != null : "TaskList should not be null";
        assert task != null : "Task should not be null";
        return "Noted, I have removed this task:" + NEWLINE + task
                + NEWLINE + tasksLeft(tasks);
    }

    /**
     * Returns formatted message to find all matching tasks.
     *
     * @param matchingTasks TaskList of tasks that match the keyword.
     * @return Formatted matching message for display.
     */
    public String matchingTasks(TaskList matchingTasks) {
        assert matchingTasks != null : "TaskList should not be null";

        StringBuilder matchString = new StringBuilder();
        if (matchingTasks.size() == NO_TASKS) {
            matchString.append("There are no matching tasks in your lists.");
        } else {
            matchString.append("Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                Task currTask = matchingTasks.get(i);
                matchString.append(NEWLINE)
                        .append(i + DISPLAY_INDEX_OFFSET)
                        .append(TASK_SEPARATOR)
                        .append(currTask);
            }
        }
        return matchString.toString();
    }


    /**
     * Returns formatted message when a place is added.
     *
     * @param place Place that was added to places.
     * @return Formatted add message for display.
     */
    public String addPlace(Place place) {
        assert place != null : "Place should not be null";

        return "Got it. I've added this visit:" + NEWLINE
                + place;
    }

    /**
     * Returns list of all places that are currently stored in places.
     *
     * @param places Places to iterate through and list.
     * @return Formatted list message for display.
     */
    public String listPlaces(PlaceList places) {
        assert places != null : "TaskList should not be null";
        assert places.size() > 0 :  "There must me tasks in the list";

        StringBuilder placeMsg = new StringBuilder();
        placeMsg.append("Here are the places you have visited:");
        for (int i = 0; i < places.size(); i++) {
            Place currPlace = places.get(i);
            placeMsg.append(NEWLINE)
                    .append(i + DISPLAY_INDEX_OFFSET)
                    .append(TASK_SEPARATOR)
                    .append(currPlace);
        }
        return placeMsg.toString();
    }

}
