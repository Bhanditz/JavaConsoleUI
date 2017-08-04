package com.gianlu.consoleui.Input;

import com.gianlu.consoleui.Answer;

public class InputAnswer extends Answer {
    private final String answer;

    public InputAnswer(String name, String answer) {
        super(name);
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "InputAnswer{" +
                "answer='" + answer + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
