package jellicent.task;

/**
 * Represents a simple task without a specific deadline.
 * <p>
 * A {@code ToDo} is a type of {@link Task} that only has a description
 * and can be marked as done or not done.
 * It provides methods to represent the task as a string for display
 * or for storing in a file.
 * </p>
 * Example usage:
 * <pre>
 *     ToDo t = new ToDo("Read a book");
 *     System.out.println(t);
 * </pre>
 */

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
