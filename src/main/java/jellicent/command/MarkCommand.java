package jellicent.command;

import jellicent.storage.Storage;
import jellicent.task.Task;
import jellicent.task.TaskList;
import jellicent.ui.Ui;

/**
 * Represents a command to mark a task as done.
 * <p>
 * A {@code MarkCommand} operates on a {@link jellicent.task.TaskList} to mark
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
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            Task markedTask = tasks.markDone(this.markNum);
            return ui.markDone(markedTask);
        } catch (IndexOutOfBoundsException e) {
            return "Oops! There are only " + tasks.size() + " tasks in the list.";
        }
    }

    public CommandType getCommandType() {
        return CommandType.MARK;
    }
}
