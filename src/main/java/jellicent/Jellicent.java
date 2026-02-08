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
     * Main controller class for Jellicent application.
     *
     * <p> This class acts as the central coordinator between the GUI, task storage, task management and command parsing.
     * It is responsible for:
     * <ul>
     *     <li>Loading saved tasks from the storage file on initialization.</li>
     *     <li>Parsing user input into commands using {@link Parser}.</li>
     *     li>Executing commands to modify tasks, update storage, and produce
     *     output messages via {@link Ui}.</li>
     *     <li>Returning structured responses ({@link CommandResponse}) for the
     *     GUI to display.</li>
     * </ul>
     *
     * <p>Jellicent does not handle direct UI rendering or file storage itself;
     * it delegates these responsibilities to {@link Ui} and {@link Storage}.
     * This class is intended to be used by the GUI layer to process user input
     * and provide corresponding responses.
     * */
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
     *
     * @param input String input from user interface.
     * @return CommandResponse associated with type of input.
     */
    public CommandResponse getCommandResponse(String input) {
        assert input != null : "Input should not be null";

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
