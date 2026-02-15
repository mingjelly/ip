package jellicent.command;

import jellicent.entry.EntryLists;
import jellicent.storage.Storage;
import jellicent.entry.task.Event;
import jellicent.entry.task.Task;
import jellicent.entry.task.TaskList;
import jellicent.ui.Ui;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Represents a command to add a new Event task.
 * <p>
 * An {@code EventCommand} creates a {@link Event} with a
 * description, start time, and end time, and adds it to the
 * {@link TaskList}. It interacts with
 * {@link jellicent.ui.Ui} to display feedback to the user and with
 * {@link jellicent.storage.Storage} to persist the updated task list.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     EventCommand cmd = new EventCommand(
 *         "Team meeting",
 *         LocalDateTime.of(2026, 2, 10, 14, 0),
 *         LocalDateTime.of(2026, 2, 10, 15, 0)
 *     );
 *     String result = cmd.execute(tasks, ui, storage);
 * </pre>
 * </p>
 */
public class EventCommand extends Command {
    private final String description;
    private final LocalDateTime from;
    private final LocalDateTime to;

    public EventCommand(String description, LocalDateTime from, LocalDateTime to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public String execute(EntryLists entryLists, Ui ui, Storage storage) {
        assert entryLists != null: "EntryList should not be null";
        assert ui != null : "Ui should not be null";
        assert storage != null : "Storage should not be null";

        TaskList tasks = entryLists.tasks();
        assert tasks != null : "TaskList should not be null";

        Task newTask = new Event(this.description, this.from, this.to);
        tasks.add(newTask);

        String displayString = ui.addTask(tasks, newTask);

        try {
            storage.saveListDataIntoFile(tasks); //io exception?
        } catch (IOException e) {
            displayString = ui.showError("Failed to save task: " + e.getMessage())
                    + displayString;
        }
        return displayString;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.EVENT;
    }
}

