package com.hurricane.components.sequencer.configure;

import com.hurricane.components.sequencer.runtime.ExceptionHandler;
import com.hurricane.components.sequencer.runtime.Reaction;

public class ExceptionHandlers {
    private static final ExceptionHandler INTERRUPT = invokeError -> Reaction.INTERRUPT;
    private static final ExceptionHandler IGNORE = invokeError -> Reaction.IGNORE;

    public static ExceptionHandler interrupt() {
        return INTERRUPT;
    }

    public static ExceptionHandler ignore() {
        return IGNORE;
    }
}
