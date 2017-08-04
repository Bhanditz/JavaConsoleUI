package com.gianlu.consoleui;

public class InvalidInputException extends Exception {
    public final boolean isReason;

    /**
     * @param message  the desired message
     * @param isReason if the {@param message} should be included in the error message
     */
    public InvalidInputException(String message, boolean isReason) {
        super(message);
        this.isReason = isReason;
    }
}
