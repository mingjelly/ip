package jellicent.task;

import java.util.ArrayList;
import java.util.Iterator;

public class TaskList implements Iterable<Task> {
    private final ArrayList<Task> tasks;
    private static final int MIN_NUM = 1;
    private static final int MIN_IDX = 0;

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
        if (num < MIN_NUM) {
            throw new IllegalArgumentException("Number cannot be less than 0");
        }
        if (num > this.tasks.size()) {
            throw new IllegalArgumentException("Number is greater than current tasklist size");
        }
        Task task = this.tasks.get(num-1);
        task.setDone();
        return task;
    }

    /**
     * Mark the specified task as undone.
     *
     * @param num Number index to signify which of the tasks in the list to unmark.
     * @return Task for further processing if required.
     *      * @throws IllegalArgumentException if num is less than 0 or greater than tasklist size.
     */
    public Task markUndone(int num) throws IllegalArgumentException {
        if (num < MIN_NUM) {
            throw new IllegalArgumentException("Number cannot be less than 0");
        }
        if (num > this.tasks.size()) {
            throw new IllegalArgumentException("Number is greater than current tasklist size");
        }
        Task task = this.tasks.get(num-1);
        task.setUndone();
        return task;
    }

    /**
     * Remove the specified task from task list.
     * @param num Number index to signify which of the tasks in the list to remove.
     * @return Task for further processing if required.
     * @throws IllegalArgumentException if num is less than 0 or greater than tasklist size.
     */
    public Task remove(int num) {
        if (num < MIN_NUM) {
            throw new IllegalArgumentException("Number cannot be less than 0");
        }
        if (num > this.tasks.size()) {
            throw new IllegalArgumentException("Number is greater than current tasklist size");
        }
        return this.tasks.remove(num-1);
    }

    /**
     * Returns the number of tasks currently in the task list.
     *
     * @return Number of tasks in the task list.
     */
    public int size() {
        return this.tasks.size();
    }

    /**
     * Returns the task specified from the task list.
     *
     * @param idx 0-indexed index to get the task, similar to ArrayList.
     * @return Task at the specified index.
     *
     */
    public Task get(int idx) {
        if (idx < MIN_IDX) {
            throw new IllegalArgumentException("Number cannot be less than 0");
        }
        if (idx >= this.tasks.size()) {
            throw new IllegalArgumentException("Number is greater than current tasklist size");
        }
        return this.tasks.get(idx);
    }

    @Override
    public Iterator<Task> iterator() {
        return this.tasks.iterator();
    }

}




