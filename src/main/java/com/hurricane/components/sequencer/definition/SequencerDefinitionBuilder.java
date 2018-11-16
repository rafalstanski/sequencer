package com.hurricane.components.sequencer.definition;

import com.hurricane.components.sequencer.ExceptionHandler;
import com.hurricane.components.sequencer.ExceptionHandlers;
import com.hurricane.components.sequencer.Initial;
import com.hurricane.components.sequencer.step.InstanceStepFactory;
import com.hurricane.components.sequencer.step.Step;
import com.hurricane.components.sequencer.step.StepFactory;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.List;

public class SequencerDefinitionBuilder<T> {
    private Initial<T> initial;
    private List<Class<? extends Step>> stepsDefinition = new ArrayList<>();
    private StepFactory stepFactory = new InstanceStepFactory();
    private ExceptionHandler exceptionHandler = ExceptionHandlers.rethrow();

    public void initial(final Initial<T> initial) {
        Validate.notNull(initial, "Initial shouldn't be null");
        this.initial = initial;
    }

    public void step(final Class<? extends Step> stepDefinition) {
        Validate.notNull(initial, "Step definition shouldn't be null");
        this.stepsDefinition.add(stepDefinition);
    }

    public void stepFactory(final StepFactory factory) {
        Validate.notNull(factory, "Step factory shouldn't be null");
        this.stepFactory = factory;
    }

    public void exceptionHandler(final ExceptionHandler exceptionHandler) {
        Validate.notNull(exceptionHandler, "Exception handler shouldn't be null");
        this.exceptionHandler = exceptionHandler;
    }

    public SequencerDefinition<T> build() {
        validateParameters();
        return new SequencerDefinition<>(
                initial, stepsDefinition, stepFactory, exceptionHandler
        );
    }

    private void validateParameters() {
        Validate.notNull(initial, "Initial must be set in order to create correct SequencerDefinition");
        //rest of parameters have default value or are protected against null values.
    }
}
