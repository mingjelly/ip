package jellicent.task;  //same package as the class being tested

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class DeadlineTest {
    @Test
    void shouldBeDone_whenCreatedWithMarked1() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2019/10/11 12:30", formatter);
        Deadline deadline = new Deadline("Read book", dateTime, 1);
        assertTrue(deadline.isDone);
    }

    @Test
    void shouldBeUndone_whenCreatedWithUnmarked1() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2019/10/11 12:30", formatter);
        Deadline deadline = new Deadline("Read book", dateTime, 0);
        assertFalse(deadline.isDone);
    }

    @Test
    void shouldMarkDoneCorrectly() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2019/10/11 12:30", formatter);
        Deadline deadline = new Deadline("Read book", dateTime);
        deadline.markAsDone();
        assertTrue(deadline.isDone);
    }

    @Test
    void shouldMarkUndoneCorrectly() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2019/10/11 12:30", formatter);
        Deadline deadline = new Deadline("Read book", dateTime);
        deadline.markAsUndone();
        assertFalse(deadline.isDone);
    }

    @Test
    void toStringShouldIncludeTypeAndStatus() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2019/10/11 12:30", formatter);
        Deadline deadline = new Deadline("Read book", dateTime);
        assertEquals("[D][ ] Read book (by: 11 Oct 2019 12:30)" , deadline.toString());

        deadline.markAsDone();
        assertEquals("[D][X] Read book (by: 11 Oct 2019 12:30)", deadline.toString());
    }

    @Test
    void toFileStringShouldReturnCorrectFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2019/10/11 12:30", formatter);
        Deadline deadline = new Deadline("Read book", dateTime);
        assertEquals("D|0|Read book|2019/10/11 12:30", deadline.toFileString());
        deadline.markAsDone();
        assertEquals("D|1|Read book|2019/10/11 12:30", deadline.toFileString());
    }
}
