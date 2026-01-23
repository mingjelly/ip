import java.util.Scanner;

public class Jellicent {
    public static void main(String[] args) {
        String name = "Jellicent";
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Hello from " + name + "\nWhat can I do for you?");
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------------------------------------------------------");
        String command = scanner.nextLine();
        while (!command.equals("bye")) {
            System.out.println("----------------------------------------------------------------------");
            System.out.println(command);
            System.out.println("----------------------------------------------------------------------");
            command = scanner.nextLine();

        }
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Bye! Hope to see you again!");
        System.out.println("----------------------------------------------------------------------");

    }
}
