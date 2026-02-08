package jellicent.command;

import jellicent.storage.Storage;
import jellicent.task.Deadline;
import jellicent.task.Task;
import jellicent.task.TaskList;
import jellicent.ui.Ui;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Represents a command to add a new Deadline task.
 * <p>
 * A {@code DeadlineCommand} creates a {@link jellicent.task.Deadline} with a
 * description and a due date/time, and adds it to the
 * {@link jellicent.task.TaskList}. It interacts with
 * {@link jellicent.ui.Ui} to display feedback to the user and with
 * {@link jellicent.storage.Storage} to persist the updated task list.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     DeadlineCommand cmd = new DeadlineCommand(
 *         "Submit report",
 *         LocalDateTime.of(2026, 2, 12, 23, 59)
 *     );
 *     String result = cmd.execute(tasks, ui, storage);
 * </pre>
 * </p>
 */
public class DeadlineCommand extends Command {
    private final String description;
    private final LocalDateTime by;

    public DeadlineCommand(String description, LocalDateTime by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        Task newTask = new Deadline(this.description, this.by);
        tasks.add(newTask);

        String displayString = ui.addTask(tasks, newTask);

        try {
            storage.saveListDataIntoFile(tasks); //io exception?
        } catch (IOException e) {
            displayString += ui.showError("Failed to save task: " + e.getMessage());
        }
        return displayString;
    }

    public CommandType getCommandType() {
        return CommandType.DEADLINE;
    }
}

