package jellicent.command;

import jellicent.entry.EntryLists;
import jellicent.storage.Storage;
import jellicent.entry.task.Task;
import jellicent.entry.task.TaskList;
import jellicent.ui.Ui;

/**
 * Represents a command to find tasks containing a specific keyword.
 * <p>
 * A {@code FindCommand} searches through a {@link TaskList}
 * for tasks whose descriptions contain the specified string. It collects all
 * matching tasks into a new {@link TaskList} and uses
 * {@link jellicent.ui.Ui} to display the results to the user.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     FindCommand cmd = new FindCommand("homework");
 *     String result = cmd.execute(tasks, ui, storage);
 * </pre>
 * </p>
 */
public class FindCommand extends Command {
    private final String string;

    public FindCommand(String string) {
        this.string = string;
    }

    @Override
    public String execute(EntryLists entryLists, Ui ui, Storage storage) {
        assert entryLists != null : "EntryLists should not be null";
        assert ui != null : "Ui should not be null";
        assert storage != null : "Storage should not be null";

        TaskList tasks = entryLists.tasks();
        assert tasks != null : "TaskList should not be null";

        TaskList matchingTasks = new TaskList();
        for (Task task: tasks) {
            if (task.contains(this.string)) {
                matchingTasks.add(task);
            }
        }
        return ui.matchingTasks(matchingTasks);
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.FIND;
    }
}

