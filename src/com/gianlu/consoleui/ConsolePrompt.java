package com.gianlu.consoleui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConsolePrompt {

    public ConsolePrompt() {
    }

    public <A extends Answer> A prompt(PromptableElement<A> prompt) throws IOException {
        return prompt.prompt(System.out, System.in);
    }

    public List<? extends Answer> prompt(PromptableElement<? extends Answer>... prompts) throws IOException {
        List<Answer> answers = new ArrayList<>();
        for (PromptableElement prompt : prompts)
            answers.add(prompt.prompt(System.out, System.in));

        return answers;
    }
}
