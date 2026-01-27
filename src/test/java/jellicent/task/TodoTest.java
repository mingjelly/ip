package jellicent.task;  //same package as the class being tested

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TodoTest {
    @Test
    void shouldBeDone_whenCreatedWithMarked1() {
        ToDo todo = new ToDo("Read book", 1);
        assertTrue(todo.isDone);
    }

    @Test
    void shouldBeUndone_whenCreatedWithUnmarked1() {
        ToDo todo = new ToDo("Read book", 0);
        assertFalse(todo.isDone);
    }

    @Test
    void shouldMarkDoneCorrectly() {
        ToDo todo = new ToDo("Read book");
        todo.markAsDone();
        assertTrue(todo.isDone);
    }

    @Test
    void shouldMarkUndoneCorrectly() {
        ToDo todo = new ToDo("Read book", 1);
        todo.markAsUndone();
        assertFalse(todo.isDone);
    }

    @Test
    void toStringShouldIncludeTypeAndStatus() {
        ToDo todo = new ToDo("Read book");
        assertEquals("[T][ ] Read book", todo.toString());

        todo.markAsDone();
        assertEquals("[T][X] Read book", todo.toString());
    }

    @Test
    void toFileStringShouldReturnCorrectFormat() {
        ToDo todo = new ToDo("Read book");
        assertEquals("T|0|Read book", todo.toFileString());

        todo.markAsDone();
        assertEquals("T|1|Read book", todo.toFileString());
    }


}
