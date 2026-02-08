package jellicent.command;

import jellicent.storage.Storage;
import jellicent.task.TaskList;
import jellicent.ui.Ui;

/**
 * Represents a command to list all tasks.
 * <p>
 * A {@code ListCommand} interacts with a {@link jellicent.task.TaskList} to
 * retrieve all tasks and uses {@link jellicent.ui.Ui} to display them to the user.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     ListCommand cmd = new ListCommand();
 *     String result = cmd.execute(tasks, ui, storage);
 * </pre>
 * </p>
 */
public class ListCommand extends Command {
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        assert tasks != null : "TaskList should not be null";
        assert ui != null : "Ui should not be null";
        assert storage != null : "Storage should not be null";

        return ui.listTasks(tasks);
    }

    public CommandType getCommandType() {
        return CommandType.LIST;
    }
}
