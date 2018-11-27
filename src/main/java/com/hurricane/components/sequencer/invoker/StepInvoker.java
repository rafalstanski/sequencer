package com.hurricane.components.sequencer.invoker;

public interface StepInvoker {
    String getName();
    InvokeResult invoke(InvokerContext context);
}
