package jellicent.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Converts the LocalDateTime into a readable string forward for toString() ui.
     *
     * @param dateTime DateTime from initialisation of tasks like Deadline/Event.
     *
     * @return String format for ui.
     */
    protected String readDateTime(LocalDateTime dateTime) {
        // inputs dateTime and converts into the necessary string for internal use
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        return dateTime.format(formatter);
    }

    /**
     * Converts DateTime into appropriate format for saving and storing into file.
     *
     * @param dateTime DateTime of tasks like Deadline/Event.
     *
     * @return String format for writing dateTime into file.
     */
    protected String storeDateTime (LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return dateTime.format(formatter);
    }

    /**
     * Gets the appropriate status icon for when a task is done. X for done and blank for undone.
     *
     * @return Status icon for string output.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Sets isDone to true to signify that a task has been done.
     */
    public void setDone() {
        this.isDone = true;
    }

    /**
     * Sets isDone to false to signify that a task has not been done.
     */
    public void setUndone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    public abstract String toFileString();
}
