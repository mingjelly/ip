package jellicent.command;

import jellicent.storage.Storage;
import jellicent.task.TaskList;
import jellicent.ui.Ui;

public abstract class Command {

    /**
     * Executes the relevant commands and returns the relevant string display message.
     *
     * @param tasklist The lists of tasks used in the program.
     * @param ui Main ui object.
     * @param storage Main object used in the program.
     * @return String display message for GUI.
     */
    public abstract String execute(TaskList tasklist, Ui ui, Storage storage);

    public abstract CommandType getCommandType();
}
