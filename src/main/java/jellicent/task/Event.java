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
        if (marked == 1) {this.markAsDone();}
    }

    public String toFileString() {
        return "E|" + (super.isDone ? 1 : 0) + "|" + super.description + "|" + storeDateTime(this.from) + "|"
                + storeDateTime(this.to);
    }



    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + readDateTime(from) + " to: " + readDateTime(this.to) + ")";
    }
}
