package com.hurricane.components.sequencer.configure;

import com.hurricane.components.sequencer.configure.step.InstanceStepFactory;
import com.hurricane.components.sequencer.configure.step.StepDefinition;
import com.hurricane.components.sequencer.configure.step.StepFactory;
import com.hurricane.components.sequencer.runtime.ExceptionHandler;
import com.hurricane.components.sequencer.runtime.Initial;
import com.hurricane.components.sequencer.runtime.Step;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.List;

class SequencerBuildConfigurer<T> {
    private Initial<T> initial;
    private List<StepDefinition> stepsDefinition = new ArrayList<>();
    private StepFactory stepFactory = new InstanceStepFactory();
    private ExceptionHandler exceptionHandler = ExceptionHandlers.interrupt();

    public void initial(final Initial<T> initial) {
        Validate.notNull(initial, "Initial shouldn't be null");
        this.initial = initial;
    }

    public void step(final Class<? extends Step> stepClass) {
        Validate.notNull(stepClass, "Step class shouldn't be null");
        this.stepsDefinition.add(StepDefinition.fromClass(stepClass));
    }

    public void step(final Step stepInstance) {
        Validate.notNull(stepInstance, "Step instance shouldn't be null");
        this.stepsDefinition.add(StepDefinition.fromInstance(stepInstance));
    }

    public void stepFactory(final StepFactory factory) {
        Validate.notNull(factory, "Step factory shouldn't be null");
        this.stepFactory = factory;
    }

    public void exceptionHandler(final ExceptionHandler exceptionHandler) {
        Validate.notNull(exceptionHandler, "Exception handler shouldn't be null");
        this.exceptionHandler = exceptionHandler;
    }

    public SequencerBuildConfiguration<T> provide() {
        validateParameters();
        return new SequencerBuildConfiguration<>(
                initial, stepsDefinition, stepFactory, exceptionHandler
        );
    }

    private void validateParameters() {
        Validate.notNull(initial, "Initial must be set in order to create correct SequencerBuildConfiguration");
        //rest of parameters have default values or are protected against null values.
    }
}
