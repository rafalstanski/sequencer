package com.hurricane.components.sequencer.invoker;

import com.hurricane.components.sequencer.invoker.builder.ConsumerDefinition;
import com.hurricane.components.sequencer.invoker.builder.ProducerDefinition;
import com.hurricane.components.sequencer.sequence.Sequence;
import lombok.Builder;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
@Data
public class BaseSequenceInvoker implements SequenceInvoker {
    private final String name;
    private final Sequence sequence;
    private final Method processingMethod;
    private final List<ConsumerDefinition> consumerDefinitions;
    private final Optional<ProducerDefinition> producerDefinition;

    @Override
    public void invoke(final InvokerContext context) {
        final List<Object> values = consumerDefinitions.stream()
                .map(consumerDefinition -> consumerDefinition.take(context))
                .collect(Collectors.toList());
        try {
            final Object returnValue = processingMethod.invoke(sequence, values.toArray());
            producerDefinition.ifPresent(definition -> definition.store(context, returnValue));
        } catch (IllegalAccessException e) {
            //TODO throw appropriate exception
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            //TODO throw appropriate exception
            throw new RuntimeException(e);
        }
    }
}
