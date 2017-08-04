package com.gianlu.consoleui;

import com.gianlu.consoleui.Input.InputValidator;
import com.sun.istack.internal.Nullable;
import org.fusesource.jansi.Ansi;

import java.io.*;

import static org.fusesource.jansi.Ansi.ansi;

public abstract class PromptableInputElement<A extends Answer> extends PromptableElement<A> {
    private final String text;
    private final InputValidator validator;

    public PromptableInputElement(String name, String text, @Nullable InputValidator validator) {
        super(name);
        this.text = text;
        this.validator = validator;
    }

    @Nullable
    private String waitForInput(InputStream in) throws IOException {
        while (in.available() == 0) ;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        return reader.readLine();
    }

    public abstract String formatText(String text);

    protected void validateInternal(String input) throws InvalidInputException {
    }

    @Override
    public final A prompt(PrintStream out, InputStream in) throws IOException {
        out.print(ansi().bg(Ansi.Color.DEFAULT).a(formatText(text)));
        out.flush();

        String input = waitForInput(in);
        try {
            validateInternal(input);
            if (validator != null && input != null && !input.isEmpty()) validator.validate(input);
            return publishAnswer(input);
        } catch (InvalidInputException ex) {
            handleInvalidInput(input, out, ex);
            return prompt(out, in);
        }
    }

    protected abstract void handleInvalidInput(@Nullable String input, PrintStream out, InvalidInputException ex);
}
