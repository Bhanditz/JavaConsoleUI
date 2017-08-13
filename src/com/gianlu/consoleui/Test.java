package com.gianlu.consoleui;

import com.gianlu.consoleui.Choice.ChoicePrompt;
import com.gianlu.consoleui.Choice.List.ListChoicePrompt;
import com.gianlu.consoleui.Confirmation.ConfirmationPrompt;
import com.gianlu.consoleui.Confirmation.Value;
import com.gianlu.consoleui.Input.InputPrompt;
import com.gianlu.consoleui.Input.InputValidator;
import org.fusesource.jansi.AnsiConsole;
import org.jetbrains.annotations.NotNull;

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
                        .required(true)
                        .text("What's your name?")
                        .build(),
                new ChoicePrompt.Builder()
                        .name("gender")
                        .text("What's your gender?")
                        .newItem().name("male").key('m').text("Male").add()
                        .newItem().name("female").key('f').text("Female").add()
                        .newItem().name("unknown").key('u').text("Unknown").add()
                        .build(),
                new ListChoicePrompt.Builder()
                        .name("pizza")
                        .text("What's your favourite drink?")
                        .defaultValue("water")
                        .newItem().name("cola").text("Cola").add()
                        .newItem().name("beer").text("Beer").add()
                        .newItem().name("wine").text("Wine").add()
                        .newItem().name("water").text("Water").add()
                        .build(),
                new InputPrompt.Builder()
                        .name("website")
                        .required(false)
                        .defaultValue("http://example.com")
                        .text("What's your website address?")
                        .validator(new InputValidator() {
                            @Override
                            public void validate(@NotNull String input) throws InvalidInputException {
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
