package com.hurricane.components.sequencer.invoker.builder;

import com.hurricane.components.sequencer.invoker.BaseSequenceInvoker;
import com.hurricane.components.sequencer.invoker.SequenceInvoker;
import com.hurricane.components.sequencer.sequence.Sequence;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

public class SequenceInvokerBuilder {
    public SequenceInvoker build(final Sequence sequence) {
        final Method processingMethod = ProcessingMethodFinder.of(sequence).find();
        final List<ConsumerDefinition> consumerDefinitions = ConsumeFinder.of(processingMethod).find();
        final Optional<ProducerDefinition> producerDefinition = ProduceFinder.of(processingMethod).find();
        return BaseSequenceInvoker.builder()
                .name(sequence.getClass().getCanonicalName())
                .sequence(sequence)
                .processingMethod(processingMethod)
                .consumerDefinitions(consumerDefinitions)
                .producerDefinition(producerDefinition)
                .build();
    }
}


