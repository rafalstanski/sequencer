package com.hurricane.components.sequencer;

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
