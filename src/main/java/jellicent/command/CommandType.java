package jellicent.command;

/**
 * Represents the types of commands available in the Jellicent application.
 * <p>
 * Each {@code CommandType} corresponds to a specific command that can be executed,
 * such as listing tasks, adding tasks, marking tasks as done/undone, or finding tasks.
 * </p>
 * <p>
 * Available command types:
 * <ul>
 *     <li>{@link #LIST} - List all tasks</li>
 *     <li>{@link #BYE} - Exit the application</li>
 *     <li>{@link #MARK} - Mark a task as done</li>
 *     <li>{@link #UNMARK} - Mark a task as not done</li>
 *     <li>{@link #TODO} - Add a ToDo task</li>
 *     <li>{@link #DEADLINE} - Add a Deadline task</li>
 *     <li>{@link #EVENT} - Add an Event task</li>
 *     <li>{@link #DELETE} - Delete a task</li>
 *     <li>{@link #FIND} - Find tasks matching a keyword</li>
 *     <li>{@link #ERROR} - Represents an invalid or unrecognized command</li>
 * </ul>
 * </p>
 */
public enum CommandType {
    LIST,
    BYE,
    MARK,
    UNMARK,
    TODO,
    DEADLINE,
    EVENT,
    DELETE,
    FIND,
    ERROR,
    VISIT,
    UNVISIT,
    VISITS
}