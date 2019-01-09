package com.hurricane.components.sequencer.configure;

import com.hurricane.components.sequencer.configure.step.StepFactory;
import com.hurricane.components.sequencer.runtime.ExceptionHandler;
import com.hurricane.components.sequencer.runtime.Initial;
import com.hurricane.components.sequencer.runtime.Sequencer;
import com.hurricane.components.sequencer.runtime.Step;
import org.apache.commons.lang3.Validate;

public class SequencerBuilder<T> {
    private final SequencerBuildConfigurer<T> configurer = new SequencerBuildConfigurer<>();
    private final SequencerFactory<T> sequencerFactory = new SequencerFactory<>();

    private SequencerBuilder(final Initial<T> initial) {
        configurer.initial(initial);
    }

    public static <I> SequencerBuilder<I> initializedBy(Initial<I> initial) {
        Validate.notNull(initial, "initial parameter shouldn't be null. Use 'standalone method' instead");
        return new SequencerBuilder<>(initial);
    }

    public static SequencerBuilder<Void> standalone() {
        return new SequencerBuilder<>(Initializer.non());
    }

    public SequencerBuilder<T> start(final Class<? extends Step> startStepClass) {
        configurer.step(startStepClass);
        return this;
    }

    public SequencerBuilder<T> start(final Step startStepInstance) {
        configurer.step(startStepInstance);
        return this;
    }

    public SequencerBuilder<T> next(final Class<? extends Step> nextStepClass) {
        configurer.step(nextStepClass);
        return this;
    }

    public SequencerBuilder<T> next(final Step nextStepInstance) {
        configurer.step(nextStepInstance);
        return this;
    }

    public SequencerBuilder<T> createBy(final StepFactory factory) {
        configurer.stepFactory(factory);
        return this;
    }

    public SequencerBuilder<T> whenError(final ExceptionHandler exceptionHandler) {
        configurer.exceptionHandler(exceptionHandler);
        return this;
    }

    public Sequencer<T> build() {
        final SequencerBuildConfiguration<T> configuration = configurer.provide();
        return sequencerFactory.create(configuration);
    }
}
