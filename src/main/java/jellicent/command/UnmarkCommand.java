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
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            Task markedTask = tasks.markUndone(this.markNum);
            return ui.markUndone(markedTask);
        }
        catch (IndexOutOfBoundsException e) {
            return "Oops! There are only " + tasks.size() + " tasks in the list.";
        }
    }

    public CommandType getCommandType() {
        return CommandType.UNMARK;
    }
}
