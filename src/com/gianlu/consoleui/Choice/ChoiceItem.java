package com.gianlu.consoleui.Choice;

import com.gianlu.consoleui.ListItem;
import com.gianlu.consoleui.Required;
import org.jetbrains.annotations.NotNull;

public class ChoiceItem implements ListItem<ChoicePrompt.Builder> {
    private final ChoicePrompt.Builder builder;
    public char key;
    public String name;
    public String text;

    public ChoiceItem(ChoicePrompt.Builder builder) {
        this.builder = builder;
    }

    @Required
    public ChoiceItem key(char key) {
        this.key = key;
        return this;
    }

    @Required
    public ChoiceItem name(@NotNull String name) {
        this.name = name;
        return this;
    }

    @Override
    public ChoicePrompt.Builder add() {
        builder.items.add(this);
        return builder;
    }

    @Required
    public ChoiceItem text(@NotNull String text) {
        this.text = text;
        return this;
    }
}
