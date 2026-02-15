package jellicent.command;

import jellicent.entry.EntryLists;
import jellicent.storage.Storage;
import jellicent.entry.task.Task;
import jellicent.entry.task.TaskList;
import jellicent.ui.Ui;

/**
 * Represents a command to mark a task as not done.
 * <p>
 * An {@code UnmarkCommand} operates on a {@link TaskList} to mark a specific
 * task as undone, identified by its task number. It interacts with {@link Ui}
 * to provide feedback to the user and {@link Storage} for persisting changes.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     UnmarkCommand cmd = new UnmarkCommand(3);
 *     String result = cmd.execute(tasks, ui, storage);
 * </pre>
 * </p>
 */
public class UnmarkCommand extends Command {
    private final int markNum;

    public UnmarkCommand(int num) {
        this.markNum = num;
    }

    @Override
    public String execute(EntryLists entryLists, Ui ui, Storage storage) {
        assert entryLists != null : "EntryLists should not be null";
        assert ui != null : "Ui should not be null";
        assert storage != null : "Storage should not be null";

        TaskList tasks = entryLists.tasks();
        assert tasks != null : "TaskList should not be null";

        try {
            Task markedTask = tasks.markUndone(this.markNum);
            return ui.markUndone(markedTask);
        }
        catch (IndexOutOfBoundsException e) {
            return "Oops! There are only " + tasks.size() + " tasks in the list.";
        }
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.UNMARK;
    }
}
