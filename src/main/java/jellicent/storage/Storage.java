package jellicent.storage;

import jellicent.task.TaskList;
import jellicent.task.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;
import java.util.ArrayList;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void saveListDataIntoFile(TaskList tasks) throws IOException {
        // Initialise file
        File file = new File(this.filePath);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        // Takes each task and writes line by line
        try (FileWriter fileWriter = new FileWriter(file)) {
            for (Task task: tasks) {
                fileWriter.write(task.toFileString() + "\n");
            }
        }
    }

    public ArrayList<String> loadFileDataIntoList() {
        // Initialise file
        File file = new File(this.filePath);

        // Load file data into arraylist for further processing
        ArrayList<String> data = new ArrayList<String>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
            return data;
        } catch (FileNotFoundException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return data;
        }
    }


}
