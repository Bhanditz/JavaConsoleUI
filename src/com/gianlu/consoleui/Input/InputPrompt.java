package com.gianlu.consoleui.Input;

import com.gianlu.consoleui.InvalidInputException;
import com.gianlu.consoleui.PromptableInputElement;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.fusesource.jansi.Ansi;

import java.io.PrintStream;

import static org.fusesource.jansi.Ansi.ansi;

public class InputPrompt extends PromptableInputElement<InputAnswer> {
    private InputPrompt(String name, String text, @Nullable InputValidator validator) {
        super(name, text, validator);
    }

    @Override
    public String formatText(String text) {
        return "? " + text + " ";
    }

    @Override
    protected InputAnswer publishAnswer(String input) throws InvalidInputException {
        return new InputAnswer(name, input);
    }

    @Override
    protected void handleInvalidInput(String input, PrintStream out, InvalidInputException ex) {
        String msg = "\"" + input + "\" is not a valid input.";
        if (ex.isReason) msg += " Reason: " + ex.getMessage();
        out.println(ansi().bg(Ansi.Color.RED).a(msg));
    }

    public static class Builder {
        private String name;
        private String text;
        private InputValidator validator;

        public Builder() {
        }

        public Builder name(@NotNull String name) {
            this.name = name;
            return this;
        }

        public Builder text(@NotNull String text) {
            this.text = text;
            return this;
        }

        public Builder validator(@Nullable InputValidator validator) {
            this.validator = validator;
            return this;
        }

        public InputPrompt build() {
            return new InputPrompt(name, text, validator);
        }
    }
}
