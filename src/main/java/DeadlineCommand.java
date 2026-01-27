import java.io.IOException;
import java.time.LocalDateTime;

public class DeadlineCommand extends Command {
    private final String description;
    private final LocalDateTime by;

    public DeadlineCommand(String description, LocalDateTime by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task newTask = new Deadline(this.description, this.by);
        tasks.add(newTask);
        ui.addTask(tasks, newTask);
        try {
            storage.saveListDataIntoFile(tasks); //io exception?
        } catch (IOException e) {
            ui.showError("Failed to save task: " + e.getMessage());
        }
    }
}

