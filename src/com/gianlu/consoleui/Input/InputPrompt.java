package com.gianlu.consoleui.Input;

import com.gianlu.consoleui.Builders.GeneralBuilder;
import com.gianlu.consoleui.InvalidInputException;
import com.gianlu.consoleui.PromptableInputElement;
import com.gianlu.consoleui.Required;
import org.fusesource.jansi.Ansi;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.PrintStream;

import static org.fusesource.jansi.Ansi.ansi;

public class InputPrompt extends PromptableInputElement<InputAnswer> {
    private final String defaultValue;
    private final boolean required;

    private InputPrompt(String name, String text, @Nullable InputValidator validator, @Nullable String defaultValue, boolean required) {
        super(name, text, validator);
        this.defaultValue = defaultValue;
        this.required = required;
    }

    @Override
    public String formatText(String text) {
        return "? " + text + (defaultValue != null ? (" [" + defaultValue + "] ") : " ");
    }

    @Override
    protected InputAnswer publishAnswer(String input) throws InvalidInputException {
        if (input == null || input.isEmpty()) {
            if (defaultValue == null) {
                throw new InvalidInputException("Selected default value but default value isn't set!", false);
            } else {
                return new InputAnswer(name, defaultValue);
            }
        } else {
            return new InputAnswer(name, input);
        }
    }

    @Override
    protected void validateInternal(String input) throws InvalidInputException {
        if (required && (input == null || input.trim().isEmpty()) && defaultValue == null)
            throw new InvalidInputException("Field is required!", true);
    }

    @Override
    protected void handleInvalidInput(String input, PrintStream out, InvalidInputException ex) {
        String msg = "\"" + input + "\" is not a valid input.";
        if (ex.isReason) msg += " Reason: " + ex.getMessage();
        out.println(ansi().bg(Ansi.Color.RED).a(msg));
    }

    public static class Builder implements GeneralBuilder<InputPrompt> {
        private String name;
        private String text;
        private InputValidator validator;
        private boolean required = false;
        private String defaultValue;

        public Builder() {
        }

        @Required
        public Builder name(@NotNull String name) {
            this.name = name;
            return this;
        }

        public Builder defaultValue(@Nullable String defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public Builder required(boolean required) {
            this.required = required;
            return this;
        }

        @Required
        public Builder text(@NotNull String text) {
            this.text = text;
            return this;
        }

        public Builder validator(@Nullable InputValidator validator) {
            this.validator = validator;
            return this;
        }

        @Override
        public InputPrompt build() {
            return new InputPrompt(name, text, validator, defaultValue, required);
        }
    }
}
