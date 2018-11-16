package com.hurricane.components.sequencer.step;

public interface StepFactory {
    Step create(Class<? extends Step> stepDefinition);
}
