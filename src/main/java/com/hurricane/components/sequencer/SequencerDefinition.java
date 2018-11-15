package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.step.InstanceStepFactory;
import com.hurricane.components.sequencer.step.Step;
import com.hurricane.components.sequencer.step.StepFactory;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data(staticConstructor = "of")
public class SequencerDefinition<T> {
    private final Initial<T> initial;
    private List<Class<? extends Step>> stepsClasses = Collections.emptyList();
    private StepFactory stepFactory = new InstanceStepFactory();
    private ExceptionHandler exceptionHandler = ExceptionHandlers.rethrow();
}