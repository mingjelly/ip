package jellicent.ui;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
 * Controller class for the main GUI of the Jellicent application.
 * <p>
 * The {@code MainWindow} class manages the JavaFX UI components defined in
 * {@code MainWindow.fxml}, including the scrollable dialog container, user input
 * field, and send button. It handles user input, displays dialog boxes for both
 * the user and Jellicent, and manages the application exit sequence.
 * </p>
 * <p>
 * Responsibilities include:
 * <ul>
 *     <li>Initializing UI components and binding properties for proper layout</li>
 *     <li>Injecting the {@link Jellicent} instance for processing user commands</li>
 *     <li>Displaying greeting messages upon application start</li>
 *     <li>Handling user input and appending dialog boxes to the conversation view</li>
 *     <li>Closing the application gracefully after a delay when a BYE command is received</li>
 * </ul>
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     MainWindow controller = fxmlLoader.getController();
 *     controller.setJellicent(jellicentInstance);
 *     controller.showGreeting();
 * </pre>
 * </p>
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Jellicent jellicent;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/cinnamoroll.png"));
    private final ImageView userImageView = new ImageView(userImage);

    private final Image jellicentImage = new Image(this.getClass().getResourceAsStream("/images/kuromi.png"));
    private final ImageView jellicentImageView = new ImageView(jellicentImage);

    /**
     * Initializes the dialog box UI elements after the FXML has been loaded.
     * <p>
     * Binds the vertical scroll of the scroll pane to the height of the dialog container,
     * ensuring the scroll automatically moves as new messages are added.
     * Also sets the size and preserves the aspect ratio for the user and Jellicent images.
     * </p>
     */
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

    /**
     * Displays a greeting message from Jellicent in the dialog container.
     * <p>
     * Retrieves a greeting string from the {@link Ui} class and adds a
     * Jellicent dialog box with the greeting text and image to the dialog container.
     * </p>
     */
    public void showGreeting() {
        Ui ui = new Ui();
        String greeting = ui.greetUser();
        dialogContainer.getChildren().add(
                DialogBox.getJellicentDialog(greeting, jellicentImage)
        );
    }

    /** Injects the Jellicent instance.
     *
     * @param j input for MainWindow.
     **/
    public void setJellicent(Jellicent j) {
        assert jellicent != null: "Jellicent should not be null!";

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
