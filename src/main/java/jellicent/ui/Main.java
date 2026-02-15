package jellicent.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import jellicent.Jellicent;

/**
 * The main JavaFX GUI application for Jellicent.
 * <p>
 * The {@code Main} class extends {@link javafx.application.Application} and sets up
 * the primary stage and scene using FXML. It initializes the {@link jellicent.Jellicent}
 * instance with the default task file path and connects it to the {@link MainWindow} controller.
 * </p>
 * <p>
 * Responsibilities include:
 * <ul>
 *     <li>Loading the FXML layout from {@code /view/MainWindow.fxml}</li>
 *     <li>Setting up the primary stage with minimum dimensions</li>
 *     <li>Initializing the controller with the Jellicent instance</li>
 *     <li>Displaying the initial greeting message</li>
 * </ul>
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     java jellicent.ui.Main
 * </pre>
 * </p>
 */
public class Main extends Application {
    private final String filePath = "data/tasks.txt";
    private final Jellicent jellicent = new Jellicent(filePath);

    @Override
    public void start(Stage stage) {
        assert stage != null : "Stage should not be null";
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));

            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setTitle("Jellicent - Your Smart Chatbot");
            stage.setScene(scene);

            stage.setMinHeight(220);
            stage.setMinWidth(417);
            // stage.setMaxWidth(417); // Add this if you didn't automatically resize elements

            MainWindow controller = fxmlLoader.getController();
            controller.setJellicent(jellicent);
            stage.show();
            controller.showGreeting();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
