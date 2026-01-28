package jellicent;

import jellicent.command.Command;
import jellicent.parser.Parser;
import jellicent.parser.ParserException;
import jellicent.storage.Storage;
import jellicent.task.TaskList;
import jellicent.ui.Ui;

import java.util.Scanner;
import java.util.ArrayList;

public class Jellicent {
    public static void main(String[] args) {
        String filePath;

        // Used to run test cases
        if (args.length > 0) {
            filePath = args[0];
        } else {
            filePath = "data/tasks.txt";
        }

        Ui ui = new Ui();
        ui.greetUser();

        Storage storage = new Storage(filePath);
        ArrayList<String> strings = storage.loadFileDataIntoList();

        Scanner scanner = new Scanner(System.in);

        TaskList tasks;
        try {
            tasks = Parser.stringsIntoTasks(strings);
        } catch (ParserException pe) {
            ui.showError(pe.getMessage());
            tasks = new TaskList();
        }

        while (scanner.hasNextLine()) {
            String userInput = scanner.nextLine();

            Command command;
            try {
                command = Parser.userInputIntoCommand(userInput);
                command.execute(tasks, ui, storage);
                if (command.isExit()) {
                    return;
                }
            } catch (ParserException | IndexOutOfBoundsException e){
                ui.showError(e.getMessage());
            }
        }


    }


}
