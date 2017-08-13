package com.gianlu.consoleui.Choice;

import com.gianlu.consoleui.InvalidInputException;
import com.gianlu.consoleui.PromptableElement;
import com.gianlu.consoleui.Required;
import org.fusesource.jansi.Ansi;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.fusesource.jansi.Ansi.ansi;

public class ChoicePrompt extends PromptableElement<ChoiceAnswer> {
    private final String text;
    private final List<ChoiceItem> items;

    public ChoicePrompt(String name, String text, List<ChoiceItem> items) {
        super(name);
        this.text = text;
        this.items = items;
    }

    @NotNull
    private String joinItems() {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (ChoiceItem item : items) {
            if (!first) builder.append(", ");
            first = false;

            builder.append(item.text)
                    .append(" [")
                    .append(item.key)
                    .append("]");
        }

        return builder.toString();
    }

    @Nullable
    private String waitForInput(InputStream in) throws IOException {
        while (in.available() == 0) ;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        return reader.readLine();
    }

    @Override
    public ChoiceAnswer prompt(PrintStream out, InputStream in) throws IOException {
        out.print(ansi().bg(Ansi.Color.DEFAULT).a("? " + text + " " + joinItems() + ": "));
        out.flush();

        String input = waitForInput(in);
        try {
            return publishAnswer(input);
        } catch (InvalidInputException ex) {
            handleInvalidInput(input, out, ex);
            return prompt(out, in);
        }
    }

    private void handleInvalidInput(String input, PrintStream out, InvalidInputException ex) {
        out.println(ansi().bg(Ansi.Color.RED).a("\"" + input + "\" is not a valid choice. Please enter one of the listed keys."));
    }

    @Override
    protected ChoiceAnswer publishAnswer(String input) throws InvalidInputException {
        if (input.length() != 1) throw new InvalidInputException("Invalid input!", false);
        for (ChoiceItem item : items)
            if (item.key == input.charAt(0))
                return new ChoiceAnswer(item.name);

        throw new InvalidInputException("Invalid key!", false);
    }

    public static class Builder extends ListBuilder<ChoicePrompt, ChoiceItem> {
        public List<ChoiceItem> items;
        private String name;
        private String text;

        private boolean validateItems() {
            for (int j = 0; j < items.size(); j++) {
                for (int k = j + 1; k < items.size(); k++) {
                    ChoiceItem kItem = items.get(k);
                    ChoiceItem jItem = items.get(j);

                    if (k != j && (Objects.equals(kItem.name, jItem.name) || kItem.key == jItem.key))
                        return false;
                }
            }

            return true;
        }

        public Builder() {
            items = new ArrayList<>();
        }

        public Builder name(@NotNull String name) {
            this.name = name;
            return this;
        }

        @Override
        public ChoiceItem newItem() {
            return new ChoiceItem(this);
        }

        @Override
        public ChoicePrompt build() {
            if (!validateItems()) throw new IllegalArgumentException("Cannot have duplicated names or keys!");
            return new ChoicePrompt(name, text, items);
        }

        @Required
        public Builder text(@NotNull String text) {
            this.text = text;
            return this;
        }
    }
}
