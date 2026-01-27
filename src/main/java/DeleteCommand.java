public class DeleteCommand extends Command {
    int deleteNum;

    public DeleteCommand(int num) {
        this.deleteNum = num;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IndexOutOfBoundsException {
        Task deleteTask = tasks.remove(this.deleteNum);
        ui.deleteTask(tasks, deleteTask);
    }
}