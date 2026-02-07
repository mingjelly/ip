package jellicent.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import jellicent.Jellicent;

/**
 * A GUI for Duke using FXML.
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
