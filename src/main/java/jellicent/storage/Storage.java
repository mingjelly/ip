package jellicent.storage;

import jellicent.task.TaskList;
import jellicent.task.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading and saving of task data to and from a file.
 * <p>
 * The {@code Storage} class is responsible for reading task data from a file
 * and writing task data back to a file. It interacts with {@link jellicent.task.TaskList}
 * and {@link jellicent.task.Task}, using their string representations for storage.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     Storage storage = new Storage("data/tasks.txt");
 *     TaskList tasks = new TaskList();
 *     storage.saveListDataIntoFile(tasks); // save tasks
 *     ArrayList<String> rawData = storage.loadFileDataIntoList(); // load tasks
 * </pre>
 * </p>
 */
public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves task data into a file (text file).
     *
     * @param tasks Task data to be saved.
     * @throws IOException when the file cannot be found
     */
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

    /**
     * Loads file data into an ArrayList for further processing.
     *
     * @return ArrayList of string data to be converted to tasks.
     */
    public ArrayList<String> loadFileDataIntoList() {
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
