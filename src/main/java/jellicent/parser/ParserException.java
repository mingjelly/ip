package jellicent.parser;

import jellicent.entry.task.Task;

/**
 * Represents an exception thrown during parsing of user input or saved file data
 * in the Jellicent application.
 * <p>
 * A {@code ParserException} is thrown when input cannot be converted into a valid
 * {@link Task} or {@link jellicent.command.Command}, or when the
 * input format is invalid or incomplete.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     try {
 *         Command cmd = Parser.userInputIntoCommand("todo");
 *     } catch (ParserException e) {
 *         System.out.println(e.getMessage());
 *     }
 * </pre>
 * </p>
 */
public class ParserException extends Exception {
    public ParserException(String message) {
        super(message);
    }
}
