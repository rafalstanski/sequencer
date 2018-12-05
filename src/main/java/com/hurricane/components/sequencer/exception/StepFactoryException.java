package com.hurricane.components.sequencer.exception;

import com.hurricane.components.sequencer.step.StepDefinition;

import java.util.Arrays;

public class StepFactoryException extends SequencerException {
    private StepFactoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public static StepFactoryException incorrectClass(final StepDefinition stepDefinition, final InstantiationException e) {
        return new StepFactoryException(createIncorrectClassMessage(stepDefinition), e);
    }

    public static StepFactoryException illegalAccess(final StepDefinition stepDefinition, final IllegalAccessException e) {
        return new StepFactoryException(createIllegalAccessMessage(stepDefinition), e);
    }

    public static StepFactoryException exceptionWhileCreating(final StepDefinition stepDefinition, final RuntimeException e) {
        return new StepFactoryException(createExceptionWhileCreatingMessage(stepDefinition), e);
    }

    private static String createIncorrectClassMessage(final StepDefinition stepDefinition) {
        return "Unable to create step instance based on " +
                "/" + stepDefinition.getInstanceClass() + "/. " +
                "Check if this class is eligible to be instantiated.";
    }

    private static String createIllegalAccessMessage(final StepDefinition stepDefinition) {
        return "Unable to create step instance based on " +
                "/" + stepDefinition.getInstanceClass() + "/ " +
                "do to illegal constructor access. " +
                "Check if step's constructor is public. " +
                "Available/defined constructors: " +
                Arrays.asList(stepDefinition.getInstanceClass().getDeclaredConstructors());
    }

    private static String createExceptionWhileCreatingMessage(final StepDefinition stepDefinition) {
        return "Unable to create step instance based on " +
                "/" + stepDefinition.getInstanceClass() + "/ " +
                "do to error while instantiating.";
    }
}
