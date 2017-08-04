package com.gianlu.consoleui;

import com.gianlu.consoleui.Confirmation.ConfirmationAnswer;
import com.gianlu.consoleui.Confirmation.ConfirmationPrompt;
import com.gianlu.consoleui.Input.InputAnswer;
import com.gianlu.consoleui.Input.InputPrompt;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        AnsiConsole.systemInstall();

        ConsolePrompt prompt = new ConsolePrompt();
        ConfirmationAnswer answer = prompt.prompt(new ConfirmationPrompt.Builder()
                .name("prova124")
                .text("This is a test. Ok?")
                .defaultVal(false)
                .build());

        InputAnswer answer1 = prompt.prompt(new InputPrompt.Builder()
                .name("prova7")
                .text("What's your name?")
                .build());

        System.out.println("ANSWER: " + answer.isConfirmed());
        System.out.println("ANSWER1: " + answer1.getAnswer());

        main(args);
    }
}
