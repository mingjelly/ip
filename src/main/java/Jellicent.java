import java.util.Scanner;

public class Jellicent {
    public static void main(String[] args) {
        String name = "Jellicent";
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Hello from " + name + "\nWhat can I do for you?");
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------------------------------------------------------");
        String command = scanner.nextLine();

        String[] commands = new String[100];
        int counter = 0;

        while (!command.equals("bye")) {
            System.out.println("----------------------------------------------------------------------");
            if (command.equals("list")) {
                for (int i=0; i < counter; i++) {
                    System.out.println(i+1 + ". " + commands[i]);
                }
            } else {
                commands[counter] = command;
                System.out.println("added: " + command);
                counter++;
            }
            System.out.println("----------------------------------------------------------------------");
            command = scanner.nextLine();

        }
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Bye! Hope to see you again!");
        System.out.println("----------------------------------------------------------------------");

    }


}
