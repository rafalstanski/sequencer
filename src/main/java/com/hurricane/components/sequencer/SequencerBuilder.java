package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.step.Step;
import com.hurricane.components.sequencer.step.StepFactory;

import java.util.ArrayList;

public class SequencerBuilder<T> {
    private final SequencerDefinition<T> definition;

    private SequencerBuilder(final Initial<T> initial) {
        definition = SequencerDefinition.of(initial);
    }

    public static <I> SequencerBuilder<I> initializedBy(Initial<I> initial) {
        return new SequencerBuilder<>(initial);
    }

    public static SequencerBuilder<Void> standalone() {
        return new SequencerBuilder<>(Initializer.non());
    }

    public SequencerBuilder<T> start(final Class<? extends Step> startStep) {
        definition.setStepsClasses(new ArrayList<>());
        return next(startStep);
    }

    public SequencerBuilder<T> next(final Class<? extends Step> nextStep) {
        definition.getStepsClasses().add(nextStep);
        return this;
    }

    public SequencerBuilder<T> createBy(final StepFactory factory) {
        definition.setStepFactory(factory);
        return this;
    }

    public SequencerBuilder<T> ignoreWhenError() {
        definition.setExceptionHandler(ExceptionHandlers.ignore());
        return this;
    }

    public SequencerBuilder<T> stopWhenError() {
        definition.setExceptionHandler(ExceptionHandlers.interrupt());
        return this;
    }

    public SequencerBuilder<T> whenError(final ExceptionHandler exceptionHandler) {
        definition.setExceptionHandler(exceptionHandler);
        return this;
    }

    public Sequencer<T> build() {
        final SequencerFactory<T> sequencerFactory = new SequencerFactory<>();
        return sequencerFactory.create(definition);
    }
}
