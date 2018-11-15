package com.hurricane.components.sequencer.invoker;

import com.hurricane.components.sequencer.invoker.builder.ProducerDefinition;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class InitialStepInvoker implements StepInvoker {
    private final ProducerDefinition producerDefinition;

    @Override
    public String getName() {
        return "initial";
    }

    @Override
    public void invoke(final InvokerContext context) {
        producerDefinition.store(context, context.getInitial());
    }
}
