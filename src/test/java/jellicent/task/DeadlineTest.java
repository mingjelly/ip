package jellicent.task;  //same package as the class being tested

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class DeadlineTest {
    @Test
    void constructor_inputMarked1_isDoneTrue() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2019/10/11 12:30", formatter);
        Deadline deadline = new Deadline("Read book", dateTime, 1);
        assertTrue(deadline.isDone);
    }

    @Test
    void constructor_inputMarked0_isDoneFalse() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2019/10/11 12:30", formatter);
        Deadline deadline = new Deadline("Read book", dateTime, 0);
        assertFalse(deadline.isDone);
    }

    @Test
    void setDone_whenCalled_isDoneTrue() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2019/10/11 12:30", formatter);
        Deadline deadline = new Deadline("Read book", dateTime);
        deadline.setDone();
        assertTrue(deadline.isDone);
    }

    @Test
    void setUndone_whenCalled_isDoneFalse() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2019/10/11 12:30", formatter);
        Deadline deadline = new Deadline("Read book", dateTime);
        deadline.setUndone();
        assertFalse(deadline.isDone);
    }

    @Test
    void toString_initialState_isCorrectString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2019/10/11 12:30", formatter);
        Deadline deadline = new Deadline("Read book", dateTime);
        assertEquals("[D][ ] Read book (by: 11 Oct 2019 12:30)" , deadline.toString());

        deadline.setDone();
        assertEquals("[D][X] Read book (by: 11 Oct 2019 12:30)", deadline.toString());
    }

    @Test
    void toFileString_initialState_isCorrectString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2019/10/11 12:30", formatter);
        Deadline deadline = new Deadline("Read book", dateTime);
        assertEquals("D|0|Read book|2019/10/11 12:30", deadline.toFileString());
        deadline.setDone();
        assertEquals("D|1|Read book|2019/10/11 12:30", deadline.toFileString());
    }
}
