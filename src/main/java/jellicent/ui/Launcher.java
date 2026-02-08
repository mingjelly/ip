package jellicent.ui;

import javafx.application.Application;
/**
 * A launcher class for the Jellicent application.
 * <p>
 * The {@code Launcher} class serves as a workaround for JavaFX classpath
 * issues by providing a standard {@code main} method to start the
 * {@link Main} application class.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     java jellicent.ui.Launcher
 * </pre>
 * </p>
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
