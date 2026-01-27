import java.io.IOException;

public class TodoCommand extends Command {
    private final String description;

    TodoCommand(String description) {
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
