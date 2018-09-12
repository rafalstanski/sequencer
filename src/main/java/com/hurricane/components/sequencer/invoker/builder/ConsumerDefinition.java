package com.hurricane.components.sequencer.invoker.builder;

import com.hurricane.components.sequencer.invoker.InvokerContext;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ConsumerDefinition {
    private final String name;
    private Class<?> type;

    public Object take(final InvokerContext context) {
        return context.get(name);
    }
}
