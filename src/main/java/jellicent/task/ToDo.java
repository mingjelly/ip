package jellicent.task;

public class ToDo extends Task {

    public ToDo(String description) {
        super(description);
    }

    public ToDo(String description, int marked) {
        super(description);
        if (marked == DONE) {
            this.setDone();
        }
    }

    @Override
    public String toFileString() {
        String status = String.valueOf(super.isDone ? DONE : NOT_DONE);
        return "T|" + status + "|" + super.description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

}
