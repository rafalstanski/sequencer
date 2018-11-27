package com.hurricane.components.sequencer.exception;

import com.hurricane.components.sequencer.step.Step;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class StepInvokeException extends SequencerException {
    private StepInvokeException(String message, Throwable cause) {
        super(message, cause);
    }

    public static StepInvokeException illegalAccess(final Method processMethod, final Step target, final IllegalAccessException e) {
        return new StepInvokeException(createIllegalAccessMessage(processMethod, target), e);
    }

    public static StepInvokeException exceptionWhileExecution(final Throwable cause) {
        return new StepInvokeException("Unable to return value because there was exception while invoking step's process method", cause);
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
