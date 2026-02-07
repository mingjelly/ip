package jellicent.task;

import java.time.LocalDateTime;

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
