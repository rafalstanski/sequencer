package com.hurricane.components.sequencer.configure;

import com.hurricane.components.sequencer.configure.step.StepDefinition;
import com.hurricane.components.sequencer.configure.step.StepFactory;
import com.hurricane.components.sequencer.runtime.ExceptionHandler;
import com.hurricane.components.sequencer.runtime.Initial;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SequencerBuildConfiguration<T> {
    private final Initial<T> initial;
    private final List<StepDefinition> stepsDefinitions;
    private final StepFactory stepFactory;
    private final ExceptionHandler exceptionHandler;
}