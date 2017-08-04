package com.gianlu.consoleui.Confirmation;

import com.gianlu.consoleui.Answer;

public class ConfirmationAnswer extends Answer {
    private final boolean confirmed;

    public ConfirmationAnswer(String name, boolean confirmed) {
        super(name);
        this.confirmed = confirmed;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
