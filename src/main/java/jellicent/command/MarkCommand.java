package jellicent.command;

import jellicent.entry.EntryLists;

import jellicent.storage.Storage;
import jellicent.entry.task.Task;
import jellicent.entry.task.TaskList;
import jellicent.ui.Ui;

import java.io.IOException;

/**
 * Represents a command to mark a task as done.
 * <p>
 * A {@code MarkCommand} operates on a {@link TaskList} to mark
 * a specific task as completed, identified by its task number. It interacts
 * with {@link jellicent.ui.Ui} to provide user feedback.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     MarkCommand cmd = new MarkCommand(2);
 *     String result = cmd.execute(tasks, ui, storage);
 * </pre>
 * </p>
 */
public class MarkCommand extends Command {
    private final int markNum;

    public MarkCommand(int num) {
        this.markNum = num;
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
            Task markedTask = tasks.markDone(this.markNum);
            message = ui.markDone(markedTask);
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
        return CommandType.MARK;
    }
}
