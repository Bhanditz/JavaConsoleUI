package com.gianlu.consoleui.Choice.List;

import com.gianlu.consoleui.ListItem;
import com.gianlu.consoleui.Required;
import org.jetbrains.annotations.NotNull;

public class ListChoiceItem implements ListItem<ListChoicePrompt.Builder> {
    private final ListChoicePrompt.Builder builder;
    public String name;
    public String text;

    public ListChoiceItem(ListChoicePrompt.Builder builder) {
        this.builder = builder;
    }

    @Required
    public ListChoiceItem name(@NotNull String name) {
        this.name = name;
        return this;
    }

    @Override
    public ListChoicePrompt.Builder add() {
        builder.items.add(this);
        return builder;
    }

    @Required
    public ListChoiceItem text(@NotNull String text) {
        this.text = text;
        return this;
    }
}
