package com.hurricane.components.sequencer.step;

public class InstanceStepFactory implements StepFactory {
    @Override
    public Step create(final StepDefinition stepDefinition) {
        if (stepDefinition.isInstanceProvided()) {
            return stepDefinition.getInstance();
        } else {
            return createInstanceFromClass(stepDefinition);
        }
    }

    private Step createInstanceFromClass(final StepDefinition stepDefinition) {
        try {
            return stepDefinition.getInstanceClass().newInstance();
        } catch (final InstantiationException e) {
            //TODO throw appropriate exception
            throw new RuntimeException(e);
        } catch (final IllegalAccessException e) {
            //TODO throw appropriate exceptions
            throw new RuntimeException(e);
        }
    }
}
