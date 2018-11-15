package com.hurricane.components.sequencer.step;

public class InstanceStepFactory implements StepFactory {
    @Override
    public Step create(Class<? extends Step> stepClass) {
        try {
            return stepClass.newInstance();
        } catch (InstantiationException e) {
            //TODO throw appropriate exception
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            //TODO throw appropriate exceptions
            throw new RuntimeException(e);
        }
    }
}
