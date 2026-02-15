package jellicent.command;

import jellicent.entry.EntryLists;
import jellicent.storage.Storage;
import jellicent.entry.task.TaskList;
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
    public String execute(EntryLists entryLists, Ui ui, Storage storage) {
        assert entryLists != null : "EntryLists should not be null";
        assert ui != null : "Ui should not be null";
        assert storage != null : "Storage should not be null";

        return ui.farewellUser();
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.BYE;
    }
}
