package com.hurricane.components.sequencer.definition;

import com.hurricane.components.sequencer.ExceptionHandler;
import com.hurricane.components.sequencer.Initial;
import com.hurricane.components.sequencer.step.StepDefinition;
import com.hurricane.components.sequencer.step.StepFactory;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class SequencerDefinition<T> {
    private final Initial<T> initial;
    private final List<StepDefinition> stepsDefinitions;
    private final StepFactory stepFactory;
    private final ExceptionHandler exceptionHandler;
}