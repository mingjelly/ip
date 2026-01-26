public class Ui {

    private void printLine() {
        System.out.println("----------------------------------------------------------------------");
    }
    public void greetUser() {
        printLine();
        System.out.println("Hello from Jellicent \nWhat can I do for you?");
        printLine();
    }

    public void farewellUser() {
        printLine();
        System.out.println("Bye! Hope to see you again!");
        printLine();
    }

    public void showError(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }

    public void listTasks(TaskList tasks) {
        printLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task currTask = tasks.get(i);
            System.out.println(i+1 + ". " + currTask);
        }
        printLine();
    }

    public void markDone(Task task) {
        printLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
        printLine();
    }

    public void markUndone(Task task) {
        System.out.println("Ok. I have marked this task as not done yet:");
        System.out.println(task);
    }

    private void tasksLeft(TaskList tasks) {
        System.out.println("Now you have " + tasks.size()
                + (tasks.size() == 1 ? " task" : " tasks") + " in the list.");

    }

    public void addTask(TaskList tasks, Task task) {
        printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        tasksLeft(tasks);
        printLine();


    }


}
