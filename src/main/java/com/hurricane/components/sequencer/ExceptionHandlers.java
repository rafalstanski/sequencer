package com.hurricane.components.sequencer;

public class ExceptionHandlers {
    private static final ExceptionHandler RETHROW = Exception -> Reaction.RETHROW;
    private static final ExceptionHandler IGNORE = Exception -> Reaction.IGNORE;
    private static final ExceptionHandler INTERRUPT = Exception -> Reaction.INTERRUPT;

    public static ExceptionHandler rethrow() {
        return RETHROW;
    }

    public static ExceptionHandler interrupt() {
        return IGNORE;
    }

    public static ExceptionHandler ignore() {
        return INTERRUPT;
    }
}
