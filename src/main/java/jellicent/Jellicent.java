package jellicent;

import jellicent.command.Command;
import jellicent.command.CommandResponse;
import jellicent.command.CommandType;
import jellicent.parser.Parser;
import jellicent.parser.ParserException;
import jellicent.storage.Storage;
import jellicent.task.TaskList;
import jellicent.ui.Ui;

import java.util.ArrayList;

public class Jellicent {
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Constructor for Jellicent.
     * Used by GUI.
     *
     * @param filePath Filepath of the text data to be saved.
     */
    public Jellicent(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        ArrayList<String> strings = storage.loadFileDataIntoList();
        TaskList tasks;
        try {
            tasks = Parser.stringsIntoTasks(strings);
        } catch (ParserException pe) {
            tasks = new TaskList();
        }
        this.tasks = tasks;
    }

    /**
     * Generates a response for the user's chat message.
     */
    public CommandResponse getCommandResponse(String input) {
        String msg;
        CommandType commandType;
        try {
            Command command = Parser.userInputIntoCommand(input);
            msg = command.execute(tasks, ui, storage);
            commandType = command.getCommandType();
        } catch (ParserException e) {
            msg = ui.showError(e.getMessage());
            commandType = CommandType.ERROR;
        }
        return new CommandResponse(msg, commandType);

    }
}
