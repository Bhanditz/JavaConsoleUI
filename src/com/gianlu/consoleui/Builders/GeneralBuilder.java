package com.gianlu.consoleui.Builders;

import com.gianlu.consoleui.Answer;
import com.gianlu.consoleui.PromptableElement;

public interface GeneralBuilder<P extends PromptableElement<? extends Answer>> {
    P build();
}
