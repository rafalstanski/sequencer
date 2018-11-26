package com.hurricane.components.sequencer;

public interface ExceptionHandler {
    Reaction handle(final StepInvokeError invokeError);
}

