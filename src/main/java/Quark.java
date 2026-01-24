import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Quark {
    private static final String SEPARATOR = "____________________________________________________________";

    public static void main(String[] args) {
        List<Task> tasks = new ArrayList<>();

        Scanner in = new Scanner(System.in);

        String logo = "Hello! I'm Quark" + System.lineSeparator()
        + "What can I do for you?" + System.lineSeparator()
        + SEPARATOR;

        System.out.println(logo);

        while (true) {
            String line = in.nextLine();

            if (line.equals("bye")) {
                break;
            }

            if (line.equals("list")) {
                for (int i = 0; i < tasks.size(); i++) {
                  System.out.println((i + 1) + ". " + tasks.get(i).getDescription());
                }
                continue;
            }

            tasks.add(new Task(line));

            String reply = SEPARATOR + System.lineSeparator()
            + "▶ added: " + line + System.lineSeparator()
            + SEPARATOR;
            
            System.out.println(reply);
        }

        String byeReply = SEPARATOR + System.lineSeparator()
        + "▶ Bye. Hope to see you again soon!" + System.lineSeparator()
        + SEPARATOR;

        System.out.println(byeReply);
    }
}
