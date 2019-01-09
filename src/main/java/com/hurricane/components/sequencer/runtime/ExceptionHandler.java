package com.hurricane.components.sequencer.runtime;

public interface ExceptionHandler {
    Reaction handle(final StepInvokeError invokeError);
}

