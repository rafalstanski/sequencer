package com.hurricane.components.sequencer.invoker;

public interface StepInvoker {
    String getName();
    void invoke(InvokerContext context);
}
