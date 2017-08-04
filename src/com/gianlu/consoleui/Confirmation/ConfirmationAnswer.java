package com.gianlu.consoleui.Confirmation;

import com.gianlu.consoleui.Answer;
import org.jetbrains.annotations.NotNull;

public class ConfirmationAnswer extends Answer {
    private final Value value;

    public ConfirmationAnswer(String name, @NotNull Value value) {
        super(name);
        this.value = value;
    }

    @NotNull
    public Value getValue() {
        return value;
    }

    public boolean isConfirmed() {
        return value == Value.YES;
    }

    @Override
    public String toString() {
        return "ConfirmationAnswer{" +
                "value=" + value +
                ", name='" + name + '\'' +
                '}';
    }
}
