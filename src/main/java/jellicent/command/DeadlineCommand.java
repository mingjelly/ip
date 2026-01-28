package jellicent.command;

import jellicent.storage.Storage;
import jellicent.task.Deadline;
import jellicent.task.Task;
import jellicent.task.TaskList;
import jellicent.ui.Ui;

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
        // Initialise deadline object and add tasks
        Task newTask = new Deadline(this.description, this.by);
        tasks.add(newTask);

        // Output add tasks via ui
        ui.addTask(tasks, newTask);

        // Save all new tasks into text file
        try {
            storage.saveListDataIntoFile(tasks); //io exception?
        } catch (IOException e) {
            ui.showError("Failed to save task: " + e.getMessage());
        }
    }
}

