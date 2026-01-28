package jellicent.command;

import jellicent.storage.Storage;
import jellicent.task.TaskList;
import jellicent.ui.Ui;

public abstract class Command {

    /**
     * Executes the relevant commands.
     *
     * @param tasklist The lists of tasks used in the program.
     * @param ui Main ui object.
     * @param storage Main object used in the program.
     */
    public abstract void execute(TaskList tasklist, Ui ui, Storage storage);

    /**
     * Checks if program should end.
     *
     * @return Boolean flag to signal the end of the program.
     */
    public boolean isExit() {
        return false;
    }
}
