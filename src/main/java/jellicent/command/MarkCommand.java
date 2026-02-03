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
