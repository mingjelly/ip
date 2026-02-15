package jellicent.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import jellicent.command.CommandType;

/**
 * Represents a dialog box in the UI consisting of a speaker's image and text.
 * <p>
 * A {@code DialogBox} contains an {@link ImageView} to represent the speaker's face
 * and a {@link Label} to display the speaker's text. It can be used to display
 * messages from the user or from Jellicent, and supports styling based on
 * {@link jellicent.command.CommandType} for different types of commands.
 * </p>
 * <p>
 * This class provides static factory methods to easily create user and Jellicent
 * dialog boxes:
 * <ul>
 *     <li>{@link #getUserDialog(String, Image)}</li>
 *     <li>{@link #getJellicentDialog(String, Image)}</li>
 *     <li>{@link #getJellicentDialog(String, Image, CommandType)}</li>
 * </ul>
 * </p>
 * Example usage:
 * <pre>
 *     Image userImage = new Image("user.png");
 *     DialogBox userDialog = DialogBox.getUserDialog("Hello!", userImage);
 * </pre>
 */

public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Factory method to create a user DialogBox with the given text and image.
     *
     * @param text the text to display in the dialog box; must not be null
     * @param img the image to display alongside the text; must not be null
     * @return a new DialogBox instance representing the user's message
     */
    public static DialogBox getUserDialog(String text, Image img) {
        assert text != null : "Text should not be null";
        assert img != null : "Image should not be null";

        return new DialogBox(text, img);
    }

    /**
     * Creates a Jellicent dialog box with a specified {@link CommandType},
     * flipping the dialog box so that the image appears on the left
     * and styling it based on the command type.
     *
     * @param text the text content of the dialog; must not be null
     * @param img the image to display in the dialog; must not be null
     * @param commandType the type of command associated with this dialog; must not be null
     * @return a {@code DialogBox} representing Jellicent's response with command-specific styling
     */
    public static DialogBox getJellicentDialog(String text, Image img, CommandType commandType) {
        assert text != null : "Text should not be null";
        assert img != null : "Image should not be null";
        assert commandType != null : "CommandType should not be null";

        var db = new DialogBox(text, img);
        db.flip();
        db.changeDialogStyle(commandType);
        return db;
    }

    /**
     * Creates a standard Jellicent dialog box without a specific command type,
     * flipping the dialog box so that the image appears on the left.
     *
     * @param text the text content of the dialog; must not be null
     * @param img the image to display in the dialog; must not be null
     * @return a {@code DialogBox} representing Jellicent's response
     */
    public static DialogBox getJellicentDialog(String text, Image img) {
        assert text != null : "Text should not be null";
        assert img != null : "Image should not be null";

        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }

    /**
     * Creates a DialogBox with the specified text and image.
     * <p>
     * Loads the FXML layout for the dialog box, sets this object as its root and controller,
     * and initializes the dialog text and display picture.
     * </p>
     *
     * @param text the text to display in the dialog box; must not be null
     * @param img the image to display alongside the text; must not be null
     */
    public DialogBox(String text, Image img) {
        assert text != null : "Text should not be null";
        assert img != null : "Image should not be null";

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    private void changeDialogStyle(CommandType commandType) {
        assert commandType != null : "CommandType should not be null";

        switch(commandType) {
            case TODO:      // fallthrough
            case DEADLINE:  // fallthrough
            case EVENT:
            case VISIT:
                dialog.getStyleClass().add("add-label");
                break;
            case MARK:      // fallthrough
            case UNMARK:
                dialog.getStyleClass().add("marked-label");
                break;
            case DELETE:
                dialog.getStyleClass().add("delete-label");
                break;
            default:
                // Do nothing
        }
    }
}
