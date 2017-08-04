package com.gianlu.consoleui.Choice.List;

import com.gianlu.consoleui.Choice.ChoiceAnswer;
import com.gianlu.consoleui.Choice.ListBuilder;
import com.gianlu.consoleui.InvalidInputException;
import com.gianlu.consoleui.PromptableElement;
import com.gianlu.consoleui.Required;
import org.fusesource.jansi.Ansi;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Objects;

import static org.fusesource.jansi.Ansi.ansi;

public class ListChoicePrompt extends PromptableElement<ChoiceAnswer> {
    private final String text;
    private final List<ListChoiceItem> items;

    public ListChoicePrompt(String name, String text, List<ListChoiceItem> items) {
        super(name);
        this.text = text;
        this.items = items;
    }

    @Override
    public ChoiceAnswer prompt(PrintStream out, InputStream in) throws IOException {
        out.println(ansi().bg(Ansi.Color.DEFAULT).a("? " + text));
        for (ListChoiceItem item : items) out.println(ansi().bg(Ansi.Color.DEFAULT).a("> " + item.text));
        out.flush();

        return null; // TODO: We need to intercept key events
    }

    @Override
    protected ChoiceAnswer publishAnswer(@Nullable String input) throws InvalidInputException {
        return null; // TODO
    }

    public static class Builder extends ListBuilder<ListChoicePrompt, ListChoiceItem> {
        private String name;
        private String text;

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

        @Required
        public ListChoicePrompt.Builder name(@NotNull String name) {
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
            return new ListChoicePrompt(name, text, items);
        }

        @Required
        public ListChoicePrompt.Builder text(@NotNull String text) {
            this.text = text;
            return this;
        }
    }
}
