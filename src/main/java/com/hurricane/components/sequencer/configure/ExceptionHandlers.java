package com.hurricane.components.sequencer.configure;

import com.hurricane.components.sequencer.runtime.ExceptionHandler;
import com.hurricane.components.sequencer.runtime.Reaction;

public class ExceptionHandlers {
    private static final ExceptionHandler IGNORE = invokeError -> Reaction.IGNORE;
    private static final ExceptionHandler INTERRUPT = invokeError -> Reaction.INTERRUPT;

    public static ExceptionHandler interrupt() {
        return IGNORE;
    }

    public static ExceptionHandler ignore() {
        return INTERRUPT;
    }
}
