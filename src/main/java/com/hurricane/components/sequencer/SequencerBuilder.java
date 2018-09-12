package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.sequence.Sequence;
import com.hurricane.components.sequencer.sequence.SequenceFactory;

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

    public SequencerBuilder<T> start(final Class<? extends Sequence> startSequence) {
        definition.setSequencesClasses(new ArrayList<>());
        return next(startSequence);
    }

    public SequencerBuilder<T> next(final Class<? extends Sequence> nextSequence) {
        definition.getSequencesClasses().add(nextSequence);
        return this;
    }

    public SequencerBuilder<T> createBy(final SequenceFactory factory) {
        definition.setSequenceFactory(factory);
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
