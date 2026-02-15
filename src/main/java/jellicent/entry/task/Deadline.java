package jellicent.entry.task;

import java.time.LocalDateTime;

/**
 * Represents a task with a specific deadline.
 * <p>
 * A {@code Deadline} is a type of {@link Task} that has an associated
 * {@link LocalDateTime} indicating when the task is due.
 * It supports marking the task as done or not done,
 * and provides methods to represent the task as a string for display
 * or for storing in a file.
 * </p>
 * Example usage:
 * <pre>
 *     Deadline d = new Deadline("Submit report", LocalDateTime.of(2026, 2, 8, 23, 59));
 *     System.out.println(d);
 * </pre>
 */
public class Deadline extends Task {
    protected LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, LocalDateTime by, int marked) {
        this(description, by);
        if (marked == DONE) {
            this.setDone();
        }
    }

    @Override
    public String toFileString() {
        return "D|" + (super.isDone ? DONE : NOT_DONE) + "|"
                + super.description + "|" + storeDateTime(this.by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + readDateTime(this.by) + ")";
    }
}
