package jellicent.command;

import jellicent.storage.Storage;
import jellicent.task.TaskList;
import jellicent.ui.Ui;

public class ListCommand extends Command {
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        assert tasks != null : "TaskList should not be null";
        assert ui != null : "Ui should not be null";
        assert storage != null : "Storage should not be null";

        return ui.listTasks(tasks);
    }

    public CommandType getCommandType() {
        return CommandType.LIST;
    }
}
