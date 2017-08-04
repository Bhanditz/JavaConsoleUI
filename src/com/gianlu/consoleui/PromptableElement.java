package com.gianlu.consoleui;

import com.sun.istack.internal.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public abstract class PromptableElement<A extends Answer> {
    protected final String name;

    public abstract A prompt(PrintStream out, InputStream in) throws IOException;

    protected abstract A publishAnswer(@Nullable String input) throws InvalidInputException;

    public PromptableElement(String name) {
        this.name = name;
    }
}
