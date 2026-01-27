package jellicent.command;

import jellicent.storage.Storage;
import jellicent.task.Task;
import jellicent.task.TaskList;
import jellicent.task.ToDo;
import jellicent.ui.Ui;

import java.io.IOException;

public class TodoCommand extends Command {
    private final String description;

    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task newTask = new ToDo(description);
        tasks.add(newTask);
        ui.addTask(tasks, newTask);
        try {
            storage.saveListDataIntoFile(tasks); //io exception?
        } catch (IOException e) {
            ui.showError("Failed to save task: " + e.getMessage());
        }
    }
}
