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
    private final List<Item> items;

    public ChoicePrompt(String name, String text, List<Item> items) {
        super(name);
        this.text = text;
        this.items = items;
    }

    private static boolean validateItems(List<Item> items) {
        for (int j = 0; j < items.size(); j++) {
            for (int k = j + 1; k < items.size(); k++) {
                Item kItem = items.get(k);
                Item jItem = items.get(j);

                if (k != j && (Objects.equals(kItem.name, jItem.name) || kItem.key == jItem.key))
                    return false;
            }
        }

        return true;
    }

    @NotNull
    private String joinItems() {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Item item : items) {
            if (!first) builder.append(", ");
            first = false;

            builder.append(item.text)
                    .append(" (")
                    .append(item.key)
                    .append(")");
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
        String msg = "\"" + input + "\" is not a valid choice.";
        if (ex.isReason) msg += " Reason: " + ex.getMessage();
        else msg += " Please enter one of the listed keys.";
        out.println(ansi().bg(Ansi.Color.RED).a(msg));
    }

    @Override
    protected ChoiceAnswer publishAnswer(String input) throws InvalidInputException {
        if (input.length() != 1) throw new InvalidInputException("Invalid input!", false);
        for (Item item : items)
            if (item.key == input.charAt(0))
                return new ChoiceAnswer(item.name);

        throw new InvalidInputException("Invalid key!", false);
    }

    public static class Item {
        private final Builder builder;
        private String name;
        private String text;
        private char key;

        public Item(Builder builder) {
            this.builder = builder;
        }

        @Required
        public Item name(@NotNull String name) {
            this.name = name;
            return this;
        }

        @Required
        public Item text(@NotNull String text) {
            this.text = text;
            return this;
        }

        @Required
        public Item key(char key) {
            this.key = key;
            return this;
        }

        public Builder add() {
            builder.items.add(this);
            return builder;
        }
    }

    public static class Builder implements GeneralBuilder<ChoicePrompt> {
        private String name;
        private List<Item> items;
        private String text;

        public Builder() {
            items = new ArrayList<>();
        }

        public Builder name(@NotNull String name) {
            this.name = name;
            return this;
        }

        public Item newItem() {
            return new Item(this);
        }

        @Override
        public ChoicePrompt build() {
            if (!validateItems(items)) throw new IllegalArgumentException("Cannot have duplicated names or keys!");
            return new ChoicePrompt(name, text, items);
        }

        @Required
        public Builder text(@NotNull String text) {
            this.text = text;
            return this;
        }
    }
}
