package com.hurricane.components.sequencer.step;

public interface StepFactory {
    Step create(final StepDefinition stepDefinition);
}
