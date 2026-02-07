package jellicent.task;

public class ToDo extends Task {

    public ToDo(String description) {
        super(description);
    }

    public ToDo(String description, int marked) {
        super(description);
        if (marked == DONE) {this.setDone();}
    }

    @Override
    public String toFileString() {

        return "T|" + (super.isDone ? DONE : NOT_DONE) + "|" + super.description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

}
