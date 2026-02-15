package jellicent.command;

import jellicent.entry.EntryLists;
import jellicent.storage.Storage;
import jellicent.entry.task.TaskList;
import jellicent.ui.Ui;

/**
 * Represents a command to list all tasks.
 * <p>
 * A {@code ListCommand} interacts with a {@link TaskList} to
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
    public String execute(EntryLists entryLists, Ui ui, Storage storage) {
        assert entryLists != null: "EntryLists should not be null";
        assert ui != null : "Ui should not be null";
        assert storage != null : "Storage should not be null";

        TaskList tasks = entryLists.tasks();
        assert tasks != null : "TaskList should not be null";

        return ui.listTasks(tasks);
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.LIST;
    }
}
