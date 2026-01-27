package jellicent.command;

import jellicent.storage.Storage;
import jellicent.task.TaskList;
import jellicent.ui.Ui;

public abstract class Command {
    public abstract void execute(TaskList tasklist, Ui ui, Storage storage);

    public boolean isExit() {
        return false;
    }
}
