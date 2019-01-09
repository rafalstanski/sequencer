package com.hurricane.components.sequencer.configure.step;

import com.hurricane.components.sequencer.exception.StepFactoryException;
import com.hurricane.components.sequencer.runtime.Step;

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
            throw StepFactoryException.incorrectClass(stepDefinition, e);
        } catch (final IllegalAccessException e) {
            throw StepFactoryException.illegalAccess(stepDefinition, e);
        } catch (final RuntimeException e) {
            throw StepFactoryException.exceptionWhileCreating(stepDefinition, e);
        }
    }
}
