package com.gianlu.consoleui.Choice.List;

import com.gianlu.consoleui.Choice.ChoiceAnswer;
import com.gianlu.consoleui.Choice.ListBuilder;
import com.gianlu.consoleui.InvalidInputException;
import com.gianlu.consoleui.PromptableElement;
import com.gianlu.consoleui.Required;
import org.fusesource.jansi.Ansi;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.List;
import java.util.Objects;

import static org.fusesource.jansi.Ansi.ansi;

public class ListChoicePrompt extends PromptableElement<ChoiceAnswer> {
    private final String text;
    private final String defaultValue;
    private final List<ListChoiceItem> items;

    public ListChoicePrompt(String name, String text, @Nullable String defaultValue, List<ListChoiceItem> items) {
        super(name);
        this.text = text;
        this.defaultValue = defaultValue;
        this.items = items;
    }

    @Nullable
    private String waitForInput(InputStream in) throws IOException {
        while (in.available() == 0) ;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        return reader.readLine();
    }

    @Override
    public ChoiceAnswer prompt(PrintStream out, InputStream in) throws IOException {
        out.println(ansi().bg(Ansi.Color.DEFAULT).a("? " + text + (defaultValue != null ? (" [" + defaultValueIndex() + "]") : "")));
        for (int i = 0; i < items.size(); i++)
            out.println(ansi().bg(Ansi.Color.DEFAULT).a("   " + i + ": " + items.get(i).text));
        out.flush();

        String input = waitForInput(in);
        try {
            return publishAnswer(input);
        } catch (InvalidInputException ex) {
            handleInvalidInput(input, out, ex);
            return prompt(out, in);
        }
    }

    private int defaultValueIndex() {
        if (defaultValue == null) return -1;
        for (int i = 0; i < items.size(); i++)
            if (Objects.equals(items.get(i).name, defaultValue))
                return i;

        return -1;
    }

    private void handleInvalidInput(String input, PrintStream out, InvalidInputException ex) {
        out.println(ansi().bg(Ansi.Color.RED).a("\"" + input + "\" is not a valid choice. Please enter a number between 0 and " + (items.size() - 1)));
    }

    @Override
    protected ChoiceAnswer publishAnswer(@Nullable String input) throws InvalidInputException {
        if (input == null && defaultValue != null)
            return new ChoiceAnswer(defaultValue);

        try {
            if (input == null) throw new InvalidInputException("Input is null while no default value is set!", false);
            int sel = Integer.parseInt(input);
            if (sel >= 0 && sel < items.size()) return new ChoiceAnswer(items.get(sel).name);
            else throw new InvalidInputException("Not in range!", false);
        } catch (NumberFormatException ex) {
            throw new InvalidInputException("Not even a number!", false);
        }
    }

    public static class Builder extends ListBuilder<ListChoicePrompt, ListChoiceItem> {
        private String name;
        private String text;
        private String defaultValue;

        public Builder() {
        }

        private boolean validateItems() {
            for (int j = 0; j < items.size(); j++) {
                for (int k = j + 1; k < items.size(); k++) {
                    ListChoiceItem kItem = items.get(k);
                    ListChoiceItem jItem = items.get(j);

                    if (k != j && Objects.equals(kItem.name, jItem.name))
                        return false;
                }
            }

            return true;
        }

        private boolean validateDefaultValue() {
            if (defaultValue == null) return true;
            for (ListChoiceItem item : items)
                if (Objects.equals(item.name, defaultValue))
                    return true;

            return false;
        }

        public Builder defaultValue(@Nullable String defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        @Required
        public Builder name(@NotNull String name) {
            this.name = name;
            return this;
        }

        @Override
        public ListChoiceItem newItem() {
            return new ListChoiceItem(this);
        }

        @Override
        public ListChoicePrompt build() {
            if (!validateItems()) throw new IllegalArgumentException("Cannot have duplicated names or keys!");
            if (!validateDefaultValue())
                throw new IllegalArgumentException("Specified defaultValue doesn't match any list item!");
            return new ListChoicePrompt(name, text, defaultValue, items);
        }

        @Required
        public Builder text(@NotNull String text) {
            this.text = text;
            return this;
        }
    }
}
