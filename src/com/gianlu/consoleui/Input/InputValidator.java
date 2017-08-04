package com.gianlu.consoleui.Input;

import com.gianlu.consoleui.InvalidInputException;

public interface InputValidator {

    /**
     * Used to validate the input.
     * If the input is valid do nothing, if it's not valid throw an {@link InvalidInputException}
     */
    void validate(String input) throws InvalidInputException;
}
