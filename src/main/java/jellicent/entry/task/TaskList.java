package jellicent.entry.task;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a list of tasks in the Jellicent application.
 * <p>
 * A {@code TaskList} manages a collection of {@link Task} objects, providing
 * methods to add, remove, mark as done/undone, retrieve tasks by index, and
 * iterate over the list. Task indices used in public methods are 1-based, while
 * internal storage uses 0-based indexing.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     TaskList tasks = new TaskList();
 *     tasks.add(new ToDo("Read a book"));
 *     tasks.add(new Deadline("Submit report", LocalDateTime.of(2026, 2, 12, 23, 59)));
 *     Task task = tasks.markDone(1); // Marks the first task as done
 *     System.out.println(task);
 * </pre>
 * </p>
 */
public class TaskList implements Iterable<Task> {
    private final ArrayList<Task> tasks;
    private static final int MIN_NUM = 1;
    private static final int MIN_IDX = 0;
    private static final int ONE_BASED_TO_ZERO_BASED_OFFSET = 1;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task into the task list.
     *
     * @param task Task received from user input.
     * @return Task for further processing if required.
     */
    public Task add(Task task) {
        assert task != null : "Task should not be null";

        this.tasks.add(task);
        return task;
    }

    /**
     * Mark the specified task as done.
     *
     * @param num Number index to signify which of the tasks in the list to mark.
     * @return Task for further processing if required.
     * @throws IllegalArgumentException if num is less than 1 or greater than tasklist size.
     */
    public Task markDone(int num) {
        validateIndexOneBased(num);
        Task task = this.tasks.get(num - ONE_BASED_TO_ZERO_BASED_OFFSET);
        task.setDone();
        return task;
    }

    /**
     * Mark the specified task as undone.
     *
     * @param num Number index to signify which of the tasks in the list to unmark.
     * @return Task for further processing if required.
     * @throws IllegalArgumentException if num is less than 0 or greater than tasklist size.
     */
    public Task markUndone(int num) throws IllegalArgumentException {
        validateIndexOneBased(num);
        Task task = this.tasks.get(num - ONE_BASED_TO_ZERO_BASED_OFFSET);
        task.setUndone();
        return task;
    }

    /**
     * Removes the task at the specified 1-based index from the task list.
     *
     * @param num 1-based index of the task to remove.
     * @return The removed Task.
     * @throws IllegalArgumentException if the index is less than 1 or greater than the current task list size.
     */
    public Task remove(int num) {
        validateIndexOneBased(num);
        return this.tasks.remove(num - ONE_BASED_TO_ZERO_BASED_OFFSET);
    }

    /**
     * Retrieves the task at the specified 0-based index.
     *
     * @param idx 0-based index of the task.
     * @return The Task at the specified index.
     * @throws IllegalArgumentException if the index is less than 0 or greater than or equal to the current task list size.
     */
    public Task get(int idx) {
        validateIndexZeroBased(idx);
        return this.tasks.get(idx);
    }

    /**
     * Returns the number of tasks currently in the task list.
     *
     * @return Number of tasks.
     */
    public int size() {
        return this.tasks.size();
    }

    @Override
    public Iterator<Task> iterator() {
        return this.tasks.iterator();
    }

    private void validateIndexOneBased(int num) {
        if (num < MIN_NUM) {
            throw new IllegalArgumentException("Number cannot be less than 1");
        }
        if (num > this.tasks.size()) {
            throw new IllegalArgumentException("Number is greater than current tasklist size");
        }
    }

    private void validateIndexZeroBased(int idx) {
        if (idx < MIN_IDX) {
            throw new IllegalArgumentException("Index cannot be less than 0");
        }
        if (idx >= tasks.size()) {
            throw new IllegalArgumentException("Index is greater than current tasklist size");
        }
    }
}




