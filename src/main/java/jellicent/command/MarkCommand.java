package jellicent.command;

import jellicent.storage.Storage;
import jellicent.task.Task;
import jellicent.task.TaskList;
import jellicent.ui.Ui;

public class MarkCommand extends Command {
    private final int markNum;

    public MarkCommand(int num) {
        this.markNum = num;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IndexOutOfBoundsException {
        try {
            Task markedTask = tasks.markDone(this.markNum);
            ui.markDone(markedTask);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException(
                    "Oops! There are only " + tasks.size() + " tasks in the list.");
        }
    }
}
