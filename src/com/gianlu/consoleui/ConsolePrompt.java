package com.gianlu.consoleui;

import java.io.IOException;

public class ConsolePrompt {

    public ConsolePrompt() {
    }

    public <A extends Answer> A prompt(PromptableElement<A> prompt) throws IOException {
        return prompt.prompt(System.out, System.in);
    }
}
