package jellicent.task;

import java.util.ArrayList;
import java.util.Iterator;

public class TaskList implements Iterable<Task> {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    // Key commands: Add task, mark task as done, unmark task, delete task
    public Task add(Task task) {
        this.tasks.add(task);
        return task;
    }

    public Task markDone(int num) {
        Task task = this.tasks.get(num-1);
        task.markAsDone();
        return task;
    }

    public Task markUndone(int num) {
        Task task = this.tasks.get(num-1);
        task.markAsUndone();
        return task;
    }

    public Task remove(int num) {
        return this.tasks.remove(num-1);
    }

    public int size() {
        return this.tasks.size();
    }

    public Task get(int idx) {
        return this.tasks.get(idx);
    }

    @Override
    public Iterator<Task> iterator() {
        return this.tasks.iterator();
    }

}




