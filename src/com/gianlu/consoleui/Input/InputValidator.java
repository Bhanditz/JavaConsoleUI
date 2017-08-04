package com.gianlu.consoleui.Input;

import com.gianlu.consoleui.InvalidInputException;
import org.jetbrains.annotations.NotNull;

public interface InputValidator {

    /**
     * Used to validate the input.
     * If the input is valid do nothing, if it's not valid throw an {@link InvalidInputException}
     *
     * @param input will always be != null && #length() > 0.
     *              If you want to disallow null or empty result use {@link InputPrompt.Builder#required(boolean)}
     */
    void validate(@NotNull String input) throws InvalidInputException;
}
