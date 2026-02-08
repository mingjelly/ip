package jellicent.command;

/**
 * Represents the response of executing a command.
 * <p>
 * A {@code CommandResponse} contains a message to be displayed to the user
 * and the {@link CommandType} of the command that generated this response.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     CommandResponse response = new CommandResponse("Task added successfully", CommandType.TODO);
 *     System.out.println(response.message());
 * </pre>
 * </p>
 */
public record CommandResponse(String message, CommandType type) {}