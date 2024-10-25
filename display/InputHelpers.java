package display;

import java.util.Locale;
import java.util.Scanner;

public class InputHelpers {
    public static final Scanner INPUT = new Scanner(System.in);

    private InputHelpers() {
    }

    // Input validation methods
    public static String askValidInput(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = INPUT.nextLine();
        } while (input.isEmpty());
        return input;
    }

    public static int askValidInteger(String prompt, String errorMessage) {
        Integer number = null;
        while (number == null) {
            System.out.print(prompt);
            try {
                number = Integer.parseInt(INPUT.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
        return number;
    }

    public static boolean askBoolean(String prompt) {
        Boolean result = null;
        while (result == null) {
            System.out.print(prompt);
            String input = INPUT.nextLine().toLowerCase(Locale.ROOT);
            result = switch (input) {
                case "yes", "y" -> true;
                case "no", "n" -> false;
                default -> {
                    System.out.println("Invalid choice.");
                    yield null;
                }
            };
        }
        return result;
    }
}
