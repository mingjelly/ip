package jellicent.command;

import jellicent.storage.Storage;
import jellicent.task.Task;
import jellicent.task.TaskList;
import jellicent.task.ToDo;
import jellicent.ui.Ui;

import java.io.IOException;

/**
 * Represents a command to add a new ToDo task.
 * <p>
 * A {@code TodoCommand} creates a {@link jellicent.task.ToDo} with the given
 * description and adds it to the {@link jellicent.task.TaskList}. It also
 * interacts with {@link jellicent.ui.Ui} to display feedback to the user and
 * with {@link jellicent.storage.Storage} to persist the updated task list.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     TodoCommand cmd = new TodoCommand("Read a book");
 *     String result = cmd.execute(tasks, ui, storage);
 * </pre>
 * </p>
 */
public class TodoCommand extends Command {
    private final String description;

    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        assert tasks != null : "TaskList should not be null";
        assert ui != null : "Ui should not be null";
        assert storage != null : "Storage should not be null";
        // Create new task object and add to task list
        Task newTask = new ToDo(description);
        tasks.add(newTask);

        String displayString = ui.addTask(tasks, newTask);

        try {
            storage.saveListDataIntoFile(tasks); //io exception?
        } catch (IOException e) {
            displayString += ui.showError("Failed to save task: " + e.getMessage());
        }
        return displayString;
    }

    public CommandType getCommandType() {
        return CommandType.TODO;
    }
}
