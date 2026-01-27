import java.time.LocalDateTime;

public class Deadline extends Task {
    protected LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, String by) {
        super(description);
        this.by = stringToDateTime(by);
    }

    public Deadline(String description, LocalDateTime by, int marked) {
        this(description, by);
        if (marked == 1) {this.markAsDone();}
    }

    public String toFileString() {
        return "D|" + (super.isDone ? 1 : 0) + "|" + super.description + "|" + storeDateTime(this.by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + readDateTime(this.by) + ")";
    }
}
