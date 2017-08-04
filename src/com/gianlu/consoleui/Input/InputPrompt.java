package com.gianlu.consoleui.Input;

import com.gianlu.consoleui.InvalidInputException;
import com.gianlu.consoleui.PromptableInputElement;

import java.io.PrintStream;

public class InputPrompt extends PromptableInputElement<InputAnswer> {
    private InputPrompt(String name, String text) {
        super(name, text);
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
    protected void handleInvalidInput(String input, PrintStream out) {
    }

    public static class Builder {
        private String name;
        private String text;

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

        public InputPrompt build() {
            if (name == null) throw new IllegalArgumentException("name can't be null!");
            if (text == null) throw new IllegalArgumentException("text can't be null!");
            return new InputPrompt(name, text);
        }
    }
}
