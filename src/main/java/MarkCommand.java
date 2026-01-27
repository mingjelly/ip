public class MarkCommand extends Command {
    private int markNum;

    public MarkCommand(int num) {
        this.markNum = num;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IndexOutOfBoundsException{
        Task markedTask = tasks.markDone(this.markNum);
        ui.markDone(markedTask);
    }
}
