package com.gianlu.consoleui;

import com.gianlu.consoleui.Confirmation.ConfirmationPrompt;
import com.gianlu.consoleui.Confirmation.Value;
import com.gianlu.consoleui.Input.InputPrompt;
import com.gianlu.consoleui.Input.InputValidator;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
        AnsiConsole.systemInstall();

        ConsolePrompt prompt = new ConsolePrompt();
        List<? extends Answer> answers = prompt.prompt(
                new ConfirmationPrompt.Builder()
                        .name("test")
                        .text("This is a test. Ok?")
                        .defaultValue(Value.YES)
                        .build(),
                new InputPrompt.Builder()
                        .name("name")
                        .text("What's your name?")
                        .build(),
                new InputPrompt.Builder()
                        .name("website")
                        .text("What's your website address?")
                        .validator(new InputValidator() {
                            @Override
                            public void validate(String input) throws InvalidInputException {
                                try {
                                    new URL(input);
                                } catch (MalformedURLException e) {
                                    throw new InvalidInputException("Not a valid address!", true);
                                }
                            }
                        })
                        .build());

        System.out.println("ANSWERS: " + answers);
    }
}
