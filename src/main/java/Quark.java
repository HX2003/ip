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
                  System.out.println((i + 1) + ". " + tasks.get(i).getDescriptionWithStatusIcon());
                }
                continue;
            }

            if (line.startsWith("mark ") || line.startsWith("unmark ")) {
                boolean isMark = line.startsWith("mark ");
                String[] words = line.split(" ");

                if (words.length != 2) {
                    continue;
                }

                try {
                    int id = Integer.parseInt(words[1]) - 1;

                    if (id >= 0 && id < tasks.size()) {
                        tasks.get(id).setDone(isMark);
                        String reply;
                        if (isMark) {
                            reply = SEPARATOR + System.lineSeparator()
                                    + "▶ Nice! I've marked this task as done:" + System.lineSeparator()
                                    + tasks.get(id).getDescriptionWithStatusIcon() + System.lineSeparator()
                                    + SEPARATOR;

                        } else {
                            reply = SEPARATOR + System.lineSeparator()
                                    + "▶ OK, I've marked this task as not done yet:" + System.lineSeparator()
                                    + tasks.get(id).getDescriptionWithStatusIcon() + System.lineSeparator()
                                    + SEPARATOR;

                        }
                        System.out.println(reply);
                    }
                } catch (NumberFormatException e) {
                    // Exception
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
