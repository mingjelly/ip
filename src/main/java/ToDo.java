public class ToDo extends Task {

    public ToDo(String description) {
        super(description);
    }

    public String toFileString() {
        return "T|" + (super.isDone ? 1 : 0) + "|" + super.description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString() ;
    }
}
