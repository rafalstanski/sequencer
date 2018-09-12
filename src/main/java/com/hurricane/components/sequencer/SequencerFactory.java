package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.invoker.InitialSequenceInvoker;
import com.hurricane.components.sequencer.invoker.SequenceInvoker;
import com.hurricane.components.sequencer.invoker.builder.InitialSequenceInvokerBuilder;
import com.hurricane.components.sequencer.invoker.builder.SequenceInvokerBuilder;
import com.hurricane.components.sequencer.sequence.Sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SequencerFactory<T> {
    private final SequenceInvokerBuilder invokerBuilder = new SequenceInvokerBuilder();
    private final InitialSequenceInvokerBuilder initialSequenceInvokerBuilder = new InitialSequenceInvokerBuilder();

    public Sequencer<T> create(final SequencerDefinition<T> definition) {
        final List<SequenceInvoker> invokers = createInvokers(definition);
        return Sequencer.of(invokers, definition.getExceptionHandler());
    }

    private List<SequenceInvoker> createInvokers(final SequencerDefinition<T> definition) {
        final List<SequenceInvoker> invokers = new ArrayList<>();
        addInitialSequenceIfNecessary(definition, invokers);
        for (final Class<? extends Sequence> sequenceClass : definition.getSequencesClasses()) {
            final Sequence sequence = definition.getSequenceFactory().create(sequenceClass);
            final SequenceInvoker invoker = invokerBuilder.build(sequence);
            invokers.add(invoker);
        }
        return invokers;
    }

    private void addInitialSequenceIfNecessary(final SequencerDefinition<T> definition, final List<SequenceInvoker> invokers) {
        final Optional<InitialSequenceInvoker> initialSequenceInvoker = initialSequenceInvokerBuilder
                .build(definition.getInitial());
        initialSequenceInvoker.ifPresent(invokers::add);
    }
}
