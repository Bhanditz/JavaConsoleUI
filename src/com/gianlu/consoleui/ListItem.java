package com.gianlu.consoleui;

import com.gianlu.consoleui.Choice.ListBuilder;

public interface ListItem<B extends ListBuilder> {
    B add();
}
