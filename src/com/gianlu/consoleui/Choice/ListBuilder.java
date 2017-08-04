package com.gianlu.consoleui.Choice;

import com.gianlu.consoleui.Answer;
import com.gianlu.consoleui.Builders.GeneralBuilder;
import com.gianlu.consoleui.PromptableElement;

import java.util.ArrayList;
import java.util.List;

public abstract class ListBuilder<P extends PromptableElement<? extends Answer>, I> implements GeneralBuilder<P> {
    public final List<I> items = new ArrayList<>();

    public abstract I newItem();
}
