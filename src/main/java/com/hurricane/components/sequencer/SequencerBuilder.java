package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.definition.SequencerDefinition;
import com.hurricane.components.sequencer.definition.SequencerDefinitionBuilder;
import com.hurricane.components.sequencer.step.Step;
import com.hurricane.components.sequencer.step.StepFactory;
import org.apache.commons.lang3.Validate;

public class SequencerBuilder<T> {
    private final SequencerDefinitionBuilder<T> definitionBuilder = new SequencerDefinitionBuilder<>();
    private final SequencerFactory<T> sequencerFactory = new SequencerFactory<>();

    private SequencerBuilder(final Initial<T> initial) {
        definitionBuilder.initial(initial);
    }

    public static <I> SequencerBuilder<I> initializedBy(Initial<I> initial) {
        Validate.notNull(initial, "initial parameter shouldn't be null. Use 'standalone method' instead");
        return new SequencerBuilder<>(initial);
    }

    public static SequencerBuilder<Void> standalone() {
        return new SequencerBuilder<>(Initializer.non());
    }

    public SequencerBuilder<T> start(final Class<? extends Step> startStep) {
        definitionBuilder.step(startStep);
        return this;
    }

    public SequencerBuilder<T> next(final Class<? extends Step> nextStep) {
        definitionBuilder.step(nextStep);
        return this;
    }

    public SequencerBuilder<T> createBy(final StepFactory factory) {
        definitionBuilder.stepFactory(factory);
        return this;
    }

    public SequencerBuilder<T> whenError(final ExceptionHandler exceptionHandler) {
        definitionBuilder.exceptionHandler(exceptionHandler);
        return this;
    }

    public Sequencer<T> build() {
        final SequencerDefinition<T> sequencerDefinition = definitionBuilder.build();
        return sequencerFactory.create(sequencerDefinition);
    }
}
