package jellicent.command;

import jellicent.storage.Storage;
import jellicent.task.Task;
import jellicent.task.TaskList;
import jellicent.ui.Ui;

public class DeleteCommand extends Command {
    int deleteNum;

    public DeleteCommand(int num) {
        this.deleteNum = num;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws IndexOutOfBoundsException {
        assert tasks != null : "TaskList should not be null";
        assert ui != null : "Ui should not be null";
        assert storage != null : "Storage should not be null";

        // Delete task from task list
        Task deleteTask = tasks.remove(this.deleteNum);

        // Delete task output in ui
        return ui.deleteTask(tasks, deleteTask);
    }

    public CommandType getCommandType() {
        return CommandType.DELETE;
    }
}