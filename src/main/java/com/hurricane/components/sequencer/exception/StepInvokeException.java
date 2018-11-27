package com.hurricane.components.sequencer.exception;

import com.hurricane.components.sequencer.step.Step;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class StepInvokeException extends SequencerException {
    private StepInvokeException(String message, Throwable cause) {
        super(message, cause);
    }

    public static StepInvokeException executionError(Method processMethod, Step target, final Throwable cause) {
        return new StepInvokeException(createExecutionErrorMessage(processMethod, target, cause), cause);
    }

    public static StepInvokeException illegalAccess(final Method processMethod, final Step target, final IllegalAccessException e) {
        return new StepInvokeException(createIllegalAccessMessage(processMethod, target), e);

    }

    private static String createExecutionErrorMessage(final Method processMethod, final Step target, final Throwable cause) {
        return "There was an error while executing step " +
                "/" + target.getClass() + "/ " +
                "Exception occurred when invoking method: " +
                processMethod.toGenericString() + ". " +
                "Exception that stopped execution: " +
                cause.getClass();
    }

    private static String createIllegalAccessMessage(final Method processMethod, final Step target) {
        return "Unable to invoke step " +
                "/" + target.getClass() + "/ " +
                "do to illegal process method access. " +
                "Check if step's process method is public. " +
                "Current modifiers: " +
                Modifier.toString(processMethod.getModifiers()) + ". " +
                "Method: " +
                processMethod.toGenericString();
    }
}
