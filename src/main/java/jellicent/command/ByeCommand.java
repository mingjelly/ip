package jellicent.command;

import jellicent.storage.Storage;
import jellicent.task.TaskList;
import jellicent.ui.Ui;

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
