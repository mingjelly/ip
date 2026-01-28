package jellicent.command;

import jellicent.storage.Storage;
import jellicent.task.Event;
import jellicent.task.Task;
import jellicent.task.TaskList;
import jellicent.ui.Ui;

import java.io.IOException;
import java.time.LocalDateTime;

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
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        // Create new Event and add task into tasklist
        Task newTask = new Event(this.description, this.from, this.to);
        tasks.add(newTask);

        // Output ui for adding task
        ui.addTask(tasks, newTask);

        // Save new tasks into text file
        try {
            storage.saveListDataIntoFile(tasks); //io exception?
        } catch (IOException e) {
            ui.showError("Failed to save task: " + e.getMessage());
        }
    }
}

