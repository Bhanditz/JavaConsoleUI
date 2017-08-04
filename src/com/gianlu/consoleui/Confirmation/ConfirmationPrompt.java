package com.gianlu.consoleui.Confirmation;

import com.gianlu.consoleui.InvalidInputException;
import com.gianlu.consoleui.PromptableInputElement;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.fusesource.jansi.Ansi;

import java.io.PrintStream;
import java.util.Objects;

import static org.fusesource.jansi.Ansi.ansi;

public class ConfirmationPrompt extends PromptableInputElement<ConfirmationAnswer> {
    private final boolean defaultVal;

    private ConfirmationPrompt(@NotNull String name, @NotNull String text, boolean defaultVal) {
        super(name, text);
        this.defaultVal = defaultVal;
    }

    private String formatChoice() {
        if (defaultVal) return "Y/n";
        else return "y/N";
    }

    @Override
    public ConfirmationAnswer publishAnswer(@Nullable String input) throws InvalidInputException {
        if (input == null || input.isEmpty()) return new ConfirmationAnswer(name, defaultVal);
        else if (Objects.equals(input.toLowerCase(), "y")) return new ConfirmationAnswer(name, true);
        else if (Objects.equals(input.toLowerCase(), "n")) return new ConfirmationAnswer(name, false);
        throw new InvalidInputException("Unhandled input: " + input);
    }

    @Override
    public String formatText(String text) {
        return "? " + text + " (" + formatChoice() + ") ";
    }

    @Override
    protected void handleInvalidInput(@Nullable String input, PrintStream out) {
        out.println(ansi().bg(Ansi.Color.RED).a(input + " is not a valid choice. Please answer Y or N."));
    }

    public static class Builder {
        private String name;
        private String text;
        private boolean defaultVal;

        public Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder defaultVal(boolean defaultVal) {
            this.defaultVal = defaultVal;
            return this;
        }

        public ConfirmationPrompt build() {
            if (name == null) throw new IllegalArgumentException("name can't be null!");
            if (text == null) throw new IllegalArgumentException("text can't be null!");
            return new ConfirmationPrompt(name, text, defaultVal);
        }
    }
}
