package jellicent.command;

import jellicent.storage.Storage;
import jellicent.task.Task;
import jellicent.task.TaskList;
import jellicent.ui.Ui;

public class UnmarkCommand extends Command {
    private final int markNum;

    public UnmarkCommand(int num) {
        this.markNum = num;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IndexOutOfBoundsException {
        try {
            Task markedTask = tasks.markUndone(this.markNum);
            ui.markUndone(markedTask);
        }
        catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException(
                    "Oops! There are only " + tasks.size() + " tasks in the list.");
        }
    }
}
