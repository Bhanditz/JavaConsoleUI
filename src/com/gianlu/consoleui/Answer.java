package com.gianlu.consoleui;

public class Answer {
    protected final String name;

    public String getName() {
        return name;
    }

    public Answer(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "name='" + name + '\'' +
                '}';
    }
}
