package jellicent.command;

import jellicent.storage.Storage;
import jellicent.task.TaskList;
import jellicent.ui.Ui;

public class ByeCommand extends Command {
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        assert tasks != null : "TaskList should not be null";
        assert ui != null : "Ui should not be null";
        assert storage != null : "Storage should not be null";

        return ui.farewellUser();
    }

    public CommandType getCommandType() {
        return CommandType.BYE;
    }
}
