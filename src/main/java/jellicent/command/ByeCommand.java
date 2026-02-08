package jellicent.command;

import jellicent.storage.Storage;
import jellicent.task.TaskList;
import jellicent.ui.Ui;

/**
 * Represents a command to exit the Jellicent application.
 * <p>
 * A {@code ByeCommand} interacts with {@link jellicent.ui.Ui} to provide a farewell
 * message to the user. Executing this command signals that the application should
 * terminate.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     ByeCommand cmd = new ByeCommand();
 *     String result = cmd.execute(tasks, ui, storage);
 * </pre>
 * </p>
 */
public class ByeCommand extends Command {
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return ui.farewellUser();
    }

    public CommandType getCommandType() {
        return CommandType.BYE;
    }
}
