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
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        assert tasks != null : "TaskList should not be null";
        assert ui != null : "Ui should not be null";
        assert storage != null : "Storage should not be null";
        // Create new task object and add to task list
        Task newTask = new ToDo(description);
        tasks.add(newTask);

        // Output ui for adding task
        String displayString = ui.addTask(tasks, newTask);

        // Save tasks into text file
        try {
            storage.saveListDataIntoFile(tasks); //io exception?
        } catch (IOException e) {
            displayString += ui.showError("Failed to save task: " + e.getMessage());
        }
        return displayString;
    }

    public CommandType getCommandType() {
        return CommandType.TODO;
    }
}
