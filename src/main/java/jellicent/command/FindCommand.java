package jellicent.command;

import jellicent.storage.Storage;
import jellicent.task.Task;
import jellicent.task.TaskList;
import jellicent.ui.Ui;

public class FindCommand extends Command {
    private final String string;

    public FindCommand(String string) {
        this.string = string;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        TaskList matchingTasks = new TaskList();
        for (Task task: tasks) {
            if (task.contains(this.string)) {
                matchingTasks.add(task);
            }
        }
        return ui.matchingTasks(matchingTasks);
    }

    public CommandType getCommandType() {
        return CommandType.FIND;
    }
}

