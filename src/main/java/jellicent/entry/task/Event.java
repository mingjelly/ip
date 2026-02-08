package jellicent.entry.task;

import java.time.LocalDateTime;

/**
 * Represents a task that occurs over a specific time period.
 * <p>
 * An {@code Event} is a type of {@link Task} that has a start time ({@code from})
 * and an end time ({@code to}). It can be marked as done or not done, and provides
 * methods to represent the task as a string for display or for storing in a file.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     Event meeting = new Event(
 *         "Team meeting",
 *         LocalDateTime.of(2026, 2, 10, 14, 0),
 *         LocalDateTime.of(2026, 2, 10, 15, 0)
 *     );
 *     System.out.println(meeting);
 * </pre>
 * </p>
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public Event(String description, LocalDateTime from, LocalDateTime to, int marked) {
        this(description, from, to);
        if (marked == DONE) {this.setDone();}
    }

    @Override
    public String toFileString() {
        String status = String.valueOf(super.isDone ? DONE : NOT_DONE);
        String fromString = storeDateTime(this.from);
        String toString = storeDateTime(this.to);
        return "E|" + status + "|" + super.description
                + "|" + fromString + "|" + toString;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + readDateTime(from)
                + " to: " + readDateTime(this.to) + ")";
    }
}
