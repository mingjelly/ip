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
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IndexOutOfBoundsException {
        // Delete task from task list
        Task deleteTask = tasks.remove(this.deleteNum);

        // Delete task output in ui
        ui.deleteTask(tasks, deleteTask);
    }
}