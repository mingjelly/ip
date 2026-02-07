package jellicent.command;

import jellicent.storage.Storage;
import jellicent.task.Task;
import jellicent.task.TaskList;
import jellicent.ui.Ui;

/**
 * Represents a command to delete a task from the task list.
 * <p>
 * A {@code DeleteCommand} removes a specific task from a
 * {@link jellicent.task.TaskList}, identified by its task number.
 * It interacts with {@link jellicent.ui.Ui} to provide feedback to the user.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     DeleteCommand cmd = new DeleteCommand(3);
 *     String result = cmd.execute(tasks, ui, storage);
 * </pre>
 * </p>
 */
public class DeleteCommand extends Command {
    int deleteNum;

    public DeleteCommand(int num) {
        this.deleteNum = num;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws IndexOutOfBoundsException {
        Task deleteTask = tasks.remove(this.deleteNum);
        return ui.deleteTask(tasks, deleteTask);
    }

    public CommandType getCommandType() {
        return CommandType.DELETE;
    }
}