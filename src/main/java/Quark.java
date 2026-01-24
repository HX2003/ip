import java.util.Scanner;

public class Quark {
    private static final String SEPARATOR = "____________________________________________________________";

    public static void main(String[] args) {
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

            String reply = SEPARATOR + System.lineSeparator()
            + "▶ " + line + System.lineSeparator()
            + SEPARATOR;
            
            System.out.println(reply);
        }

        String byeReply = SEPARATOR + System.lineSeparator()
        + "▶ Bye. Hope to see you again soon!" + System.lineSeparator()
        + SEPARATOR;

        System.out.println(byeReply);
    }
}
