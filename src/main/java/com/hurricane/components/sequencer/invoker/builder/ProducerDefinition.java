package com.hurricane.components.sequencer.invoker.builder;

import com.hurricane.components.sequencer.invoker.InvokerContext;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProducerDefinition {
    private final String name;

    public void store(final InvokerContext context, final Object value) {
        context.put(name, value);
    }
}
