package com.gianlu.consoleui.Confirmation;

import com.gianlu.consoleui.Choice.ChoicePrompt;
import com.gianlu.consoleui.InvalidInputException;
import com.gianlu.consoleui.PromptableInputElement;
import com.gianlu.consoleui.Required;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.fusesource.jansi.Ansi;

import java.io.PrintStream;
import java.util.Objects;

import static org.fusesource.jansi.Ansi.ansi;

public class ConfirmationPrompt extends PromptableInputElement<ConfirmationAnswer> {
    private final Value defaultValue;

    private ConfirmationPrompt(@NotNull String name, @NotNull String text, @Nullable Value defaultValue) {
        super(name, text, null);
        this.defaultValue = defaultValue;
    }

    private String formatChoice() {
        if (defaultValue == null) return "y/n";
        else if (defaultValue == Value.YES) return "Y/n";
        else return "y/N";
    }

    @Override
    public ConfirmationAnswer publishAnswer(@Nullable String input) throws InvalidInputException {
        if (input == null || input.isEmpty()) {
            if (defaultValue == null) {
                throw new InvalidInputException("Selected default value but default value isn't set!", false);
            } else {
                return new ConfirmationAnswer(name, defaultValue);
            }
        } else {
            if (Objects.equals(input.toLowerCase(), "y")) return new ConfirmationAnswer(name, Value.YES);
            else if (Objects.equals(input.toLowerCase(), "n")) return new ConfirmationAnswer(name, Value.NO);
        }

        throw new InvalidInputException("Unhandled input: " + input, false);
    }

    @Override
    public String formatText(String text) {
        return "? " + text + " (" + formatChoice() + "): ";
    }

    @Override
    protected void handleInvalidInput(@Nullable String input, PrintStream out, InvalidInputException ex) {
        out.println(ansi().bg(Ansi.Color.RED).a("\"" + input + "\" is not a valid choice. Please enter Y or N."));
    }

    public static class Builder implements GeneralBuilder<ConfirmationPrompt> {
        private String name;
        private String text;
        private Value defaultValue;

        public Builder() {
        }

        @Required
        public Builder name(@NotNull String name) {
            this.name = name;
            return this;
        }

        public Builder defaultValue(@Nullable Value defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        @Required
        public Builder text(@NotNull String text) {
            this.text = text;
            return this;
        }

        @Override
        public ConfirmationPrompt build() {
            return new ConfirmationPrompt(name, text, defaultValue);
        }
    }
}
