package display;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;

public class ChoiceDisplay {
    private static final Runnable NO_OP = () -> {
    };

    private final String prompt;
    private final Map<String, Runnable> choices = new HashMap<>();
    private boolean ignoreCase = false;
    private Predicate<String> defaultHandler;
    private Runnable unknownInputHandler;

    private ChoiceDisplay(String prompt) {
        this.prompt = prompt;
    }

    public static ChoiceDisplay begin(String prompt) {
        return new ChoiceDisplay(prompt);
    }

    public ChoiceDisplay setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
        return this;
    }

    public ChoiceDisplay setDefaultHandler(Predicate<String> defaultHandler) {
        this.defaultHandler = defaultHandler;
        return this;
    }

    public ChoiceDisplay setUnknownInputHandler(Runnable unknownInputHandler) {
        this.unknownInputHandler = unknownInputHandler;
        return this;
    }

    public ChoiceDisplay addChoice(Runnable runnable, String choice, String... additionalChoices) {
        choices.put(choice, runnable);
        for (String additionalChoice : additionalChoices) {
            choices.put(additionalChoice, runnable);
        }
        return this;
    }

    public ChoiceDisplay acknowledgeChoice(String choice, String... additionalChoices) {
        return addChoice(NO_OP, choice, additionalChoices);
    }

    protected String normalizeCase(String input) {
        return ignoreCase ? input.toLowerCase(Locale.ROOT) : input;
    }

    public String run() {
        // Create a new map with normalized input, if needed
        Map<String, Runnable> choices = this.choices;
        if (ignoreCase) {
            choices = new HashMap<>();
            for (Map.Entry<String, Runnable> entry : this.choices.entrySet()) {
                choices.put(normalizeCase(entry.getKey()), entry.getValue());
            }
        }

        String lastInput;
        boolean validInput = false;
        do {
            System.out.print(prompt);
            lastInput = normalizeCase(InputHelpers.INPUT.nextLine());

            // Check known choices
            Runnable foundChoice = choices.get(lastInput);
            if (foundChoice != null) {
                foundChoice.run();
                validInput = true;
            }

            // Try the default handler
            if (!validInput && defaultHandler != null) {
                validInput = defaultHandler.test(lastInput);
            }

            // Invalid choice; try again
            if (!validInput) {
                unknownInputHandler.run();
            }
        } while (!validInput);
        return lastInput;
    }
}
