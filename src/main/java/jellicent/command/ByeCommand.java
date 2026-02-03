package jellicent.command;

import jellicent.storage.Storage;
import jellicent.task.TaskList;
import jellicent.ui.Ui;

public class ByeCommand extends Command {
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return ui.farewellUser();
    }

    @Override
    public boolean isExit() {
        return true;
    }

    public CommandType getCommandType() {
        return CommandType.BYE;
    }
}
