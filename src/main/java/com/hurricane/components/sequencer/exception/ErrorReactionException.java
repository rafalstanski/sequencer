package com.hurricane.components.sequencer.exception;

import com.hurricane.components.sequencer.runtime.Reaction;
import com.hurricane.components.sequencer.runtime.StepInvokeError;

public class ErrorReactionException extends SequencerException {
    private ErrorReactionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public static ErrorReactionException unsupportedReaction(
            final Reaction reaction,
            final StepInvokeError invokeError) {
        return new ErrorReactionException(
                createUnsupportedReactionMessage(reaction, invokeError), invokeError.getCause());
    }

    private static String createUnsupportedReactionMessage(
            final Reaction reaction,
            final StepInvokeError invokeError) {
        return "Unsupported reaction from error handler: " +
                reaction + ". " +
                "Exception occurred when invoking step: " +
                invokeError.getStepName() + ". " +
                "Reaction was created do to: " +
                invokeError.getCause().getClass();
    }
}
