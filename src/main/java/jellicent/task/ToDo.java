package jellicent.task;

public class ToDo extends Task {

    public ToDo(String description) {
        super(description);
    }

    public ToDo(String description, int marked) {
        super(description);
        if (marked == 1) {this.setDone();}
    }

    public String toFileString() {
        return "T|" + (super.isDone ? 1 : 0) + "|" + super.description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

}
