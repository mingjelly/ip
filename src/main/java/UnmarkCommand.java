public class UnmarkCommand extends Command {
    private final int markNum;

    public UnmarkCommand(int num) {
        this.markNum = num;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IndexOutOfBoundsException {
        Task markedTask = tasks.markUndone(this.markNum);
        ui.markUndone(markedTask);
    }
}
