package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.invoker.InvokerContext;
import com.hurricane.components.sequencer.invoker.StepInvoker;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(staticName = "of")
public class Sequencer<T> {
    private final List<StepInvoker> invokers;
    private final ExceptionHandler exceptionHandler;
    private final ContextInitalPopulator populator;

    public SequencerResult start(T initialValue) {
        final InvokerContext context = createContext(initialValue);
        final InvokersRunner runner = InvokersRunner.basedOn(invokers, exceptionHandler);
        runner.run(context);
        return createResult(runner, context);
    }

    private InvokerContext createContext(final Object initialValue) {
        final InvokerContext context = new InvokerContext();
        populator.populate(context, initialValue);
        return context;
    }

    private SequencerResult createResult(final InvokersRunner runner, final InvokerContext context) {
        return SequencerResult.builder()
                .artifacts(context.getArtifacts().values())
                .errors(runner.getErrors())
                .executedSteps(runner.getExecutedStepsNames())
                .build();
    }
}


