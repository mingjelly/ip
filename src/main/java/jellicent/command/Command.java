package jellicent.command;

import jellicent.entry.EntryLists;
import jellicent.storage.Storage;
import jellicent.ui.Ui;

/**
 * Represents an abstract command that can be executed in the Jellicent application.
 * <p>
 * A {@code Command} defines the structure for all concrete commands, including
 * execution logic via {@link #execute(EntryLists, Ui, Storage)} and identifying
 * its type via {@link #getCommandType()}.
 * </p>
 * <p>
 * All specific commands (e.g., {@link TodoCommand}, {@link EventCommand},
 * {@link MarkCommand}) should extend this class and implement the abstract methods.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     Command cmd = new TodoCommand("Read a book");
 *     String result = cmd.execute(tasks, ui, storage);
 * </pre>
 * </p>
 */
public abstract class Command {

    /**
     * Executes the relevant commands and returns the relevant string display message.
     *
     * @param entryLists The lists of entries such as tasks and places used in the program.
     * @param ui Main ui object.
     * @param storage Main object used in the program.
     * @return String display message for GUI.
     */
    public abstract String execute(EntryLists entryLists, Ui ui, Storage storage);

    public abstract CommandType getCommandType();
}
