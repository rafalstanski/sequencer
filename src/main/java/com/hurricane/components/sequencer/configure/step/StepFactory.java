package com.hurricane.components.sequencer.configure.step;

import com.hurricane.components.sequencer.runtime.Step;

public interface StepFactory {
    Step create(final StepDefinition stepDefinition);
}
