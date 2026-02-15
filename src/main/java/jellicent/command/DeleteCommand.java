package jellicent.command;

import jellicent.entry.EntryLists;
import jellicent.storage.Storage;
import jellicent.entry.task.Task;
import jellicent.entry.task.TaskList;
import jellicent.ui.Ui;

import java.io.IOException;

/**
 * Represents a command to delete a task from the task list.
 *
 * <p>
 * A {@code DeleteCommand} removes the task at the specified index
 * from the {@link TaskList} and returns a string message for display
 * through the GUI indicating which task was deleted and the updated task list.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 *     DeleteCommand cmd = new DeleteCommand(2);
 *     String displayMessage = cmd.execute(entryLists, ui, storage);
 *     System.out.println(displayMessage);
 * </pre>
 * </p>
 */
public class DeleteCommand extends Command {
    int deleteNum;

    public DeleteCommand(int num) {
        this.deleteNum = num;
    }

    @Override
    public String execute(EntryLists entryLists, Ui ui, Storage storage) {
        assert entryLists != null : "EntryLists should not be null";
        assert ui != null : "Ui should not be null";
        assert storage != null : "Storage should not be null";

        TaskList tasks = entryLists.tasks();
        assert tasks != null : "TaskList should not be null";

        String message;
        try {
            Task deleteTask = tasks.remove(this.deleteNum);
            message = ui.deleteTask(tasks, deleteTask);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Oops! There are only " + tasks.size() + " tasks in the list.");
        }

        try {
            storage.saveListDataIntoFile(tasks);
        } catch (IOException e) {
            message = "Warning, failed to save tasks" + "\n" + message;
        }
        return message;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.DELETE;
    }
}