package com.hurricane.components.sequencer.runtime;

import com.hurricane.components.sequencer.runtime.invoker.InvokerContext;
import com.hurricane.components.sequencer.runtime.invoker.InvokersRunner;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class Sequencer<T> {
    private final SequencerContract definition;

    public SequencerResult start(T initialValue) {
        final InvokerContext context = createContext(initialValue);
        final InvokersRunner runner = InvokersRunner
                .basedOn(definition.getInvokers(), definition.getExceptionHandler());
        return runner.run(context);
    }

    private InvokerContext createContext(final Object initialValue) {
        final InvokerContext context = new InvokerContext();
        definition.getPopulator().populate(context, initialValue);
        return context;
    }
}