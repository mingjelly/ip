public class ByeCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.farewellUser();
        // Exit ui?
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
