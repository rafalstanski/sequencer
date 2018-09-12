package com.hurricane.components.sequencer.invoker;

public interface SequenceInvoker {
    String getName();
    void invoke(InvokerContext context);
}
