# Jellicent User Guide

*![Ui.png](Ui.png)*

**Jellicent** is a personal task and visit management assistant. It allows you to manage tasks like ToDos, Deadlines, and Events, and also track places you plan to visit. The app provides both a GUI and command-line interface, with clear visual feedback and error handling.

---

## Table of Contents

1. [Adding Tasks](#adding-tasks)
2. [Listing Tasks](#listing-tasks)
3. [Marking Tasks Done/Undone](#marking-tasks-doneundone)
4. [Deleting Tasks](#deleting-tasks)
5. [Adding Places](#adding-places)
6. [Listing Places](#listing-places)
7. [Removing Places](#removing-places)
8. [Error Handling](#error-handling)
9. [Exiting Jellicent](#exiting-jellicent)

---

## Adding Tasks

You can add three types of tasks: ToDo, Deadline, and Event.

### ToDo
**Command:**  
todo <description>

**Example:**  
todo Read a book

**Outcome:**  
Got it. I've added this task:
[T][ ] Read a book
Now you have 1 task in the list.


### Deadline
**Command:**  
deadline <description> /by <yyyy-mm-dd HH:mm>

**Example:**  
deadline Submit report /by 2026-02-12 23:59

**Outcome:**  
Got it. I've added this task:
[D][ ] Submit report (by: 12 Feb 2026, 23:59)
Now you have 2 tasks in the list.


### Event
**Command:**  
event <description> /at <yyyy-mm-dd HH:mm>

**Example:**  
event Team meeting /at 2026-02-15 15:00

**Outcome:**  
Got it. I've added this task:
[E][ ] Team meeting (at: 15 Feb 2026, 15:00)
Now you have 3 tasks in the list.


---

## Listing Tasks

**Command:**  
list

**Outcome:**  
Here are the tasks in your list:

[T][ ] Read a book

[D][ ] Submit report (by: 12 Feb 2026, 23:59)

[E][ ] Team meeting (at: 15 Feb 2026, 15:00)


---

## Marking Tasks Done/Undone

### Mark Done
**Command:**  
mark <task_number>

**Example:**  
mark 2

**Outcome:**  
Nice! I've marked this task as done:
[D][X] Submit report (by: 12 Feb 2026, 23:59)


### Mark Undone
**Command:**  
unmark <task_number>

**Example:**  
unmark 2

**Outcome:**  
Ok. I have marked this task as not done yet:
[D][ ] Submit report (by: 12 Feb 2026, 23:59)


---

## Deleting Tasks

**Command:**  
delete <task_number>

**Example:**  
delete 3

**Outcome:**  
Noted, I have removed this task:
[E][ ] Team meeting (at: 15 Feb 2026, 15:00)
Now you have 2 tasks in the list.


> ⚠ If you enter an invalid index, the GUI/CLI will show an error message:  
Oops! There are only 2 tasks in the list.


---

## Adding Places

**Command:**  
visit <place_name>

**Example:**  
visit Eiffel Tower

**Outcome:**  
Got it. I've added this visit:
Eiffel Tower


---

## Listing Places

**Command:**  
visits

**Outcome:**  
Here are the places you have visited:

Eiffel Tower

Louvre Museum


---

## Removing Places

**Command:**  
unvisit <place_number>

**Example:**  
unvisit 1

**Outcome:**  
Noted, I have removed this visit:
Eiffel Tower


> ⚠ Invalid indices are handled with error messages similar to tasks.

---

## Error Handling

- Invalid commands, missing arguments, or invalid indices for tasks and places are caught and displayed in a styled error dialog in the GUI or CLI.
- Example:  
  delete 5

**Outcome:**  
Oops! There are only 2 tasks in the list.


- Invalid input format for deadlines or events:  
  deadline Finish project

**Outcome:**  
Invalid format! Please use: deadline <description> /by <yyyy-mm-dd HH:mm>


- All error messages are displayed in **red-styled dialogs** in the GUI and clearly in the CLI.

---

## Exiting Jellicent

**Command:**  
bye

**Outcome:**
- GUI: Displays farewell message, waits 1 second, then closes the window.
- CLI: Prints farewell message and terminates program.

Bye! Hope to see you again!

