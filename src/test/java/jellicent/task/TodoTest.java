package jellicent.task;  //same package as the class being tested

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TodoTest {
    @Test
    void constructor_inputMarked1_isDoneTrue() {
        ToDo todo = new ToDo("Read book", 1);
        assertTrue(todo.isDone);
    }

    @Test
    void constructor_inputMarked0_isDoneFalse() {
        ToDo todo = new ToDo("Read book", 0);
        assertFalse(todo.isDone);
    }

    @Test
    void setDone_whenCalled_isDoneTrue() {
        ToDo todo = new ToDo("Read book");
        todo.setDone();
        assertTrue(todo.isDone);
    }

    @Test
    void setUndone_whenCalled_isDoneFalse() {
        ToDo todo = new ToDo("Read book", 1);
        todo.setUndone();
        assertFalse(todo.isDone);
    }

    @Test
    void toString_initialState_isCorrectString() {
        ToDo todo = new ToDo("Read book");
        assertEquals("[T][ ] Read book", todo.toString());

        todo.setDone();
        assertEquals("[T][X] Read book", todo.toString());
    }

    @Test
    void toFileString_initialState_isCorrectString() {
        ToDo todo = new ToDo("Read book");
        assertEquals("T|0|Read book", todo.toFileString());

        todo.setDone();
        assertEquals("T|1|Read book", todo.toFileString());
    }


}
