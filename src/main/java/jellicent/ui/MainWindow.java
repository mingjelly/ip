package jellicent.ui;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import jellicent.Jellicent;
import jellicent.command.CommandResponse;
import jellicent.command.CommandType;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private Jellicent jellicent;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/cinnamoroll.png"));

    private Image jellicentImage = new Image(this.getClass().getResourceAsStream("/images/kuromi.png"));
    private ImageView userImageView = new ImageView(userImage);
    private ImageView jellicentImageView = new ImageView(jellicentImage);

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        userImageView.setFitWidth(80);
        userImageView.setFitHeight(80);
        userImageView.setPreserveRatio(true);  // keeps aspect ratio
        jellicentImageView.setFitWidth(80);
        jellicentImageView.setFitHeight(80);
        jellicentImageView.setPreserveRatio(true);
    }

    /** Injects the Duke instance */
    public void setJellicent(Jellicent j) {
        jellicent = j;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        CommandResponse response = jellicent.getCommandResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getJellicentDialog(response.message(), jellicentImage, response.type())
        );
        userInput.clear();
        if (response.type() == CommandType.BYE) {
            Stage stage = (Stage) dialogContainer.getScene().getWindow();

            // Delay so user can see the message
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(e -> stage.close());
            delay.play();
        }
    }

}
