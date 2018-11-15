package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.invoker.InvokerContext;
import com.hurricane.components.sequencer.invoker.StepInvoker;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(staticName = "of")
public class Sequencer<T> {
    private final List<StepInvoker> invokers;
    private final ExceptionHandler exceptionHandler;

    public SequencerResult start(T initialValue) {
        final InvokerContext context = InvokerContext.of(initialValue);
        final SequenceRepeater repeater = SequenceRepeater.basedOn(invokers, exceptionHandler);
        do {
            final StepInvoker invoker = repeater.provide();
            try {
                invoker.invoke(context);
            } catch (RuntimeException e) {
                repeater.react(e);
            }
        } while (repeater.shouldContinue());
        return createResult(repeater, context);
    }

    private SequencerResult createResult(final SequenceRepeater repeater, final InvokerContext context) {
        return SequencerResult.builder()
                .artifacts(context.getArtifacts())
                .errors(repeater.getErrors())
                .usedSteps(repeater.getProvidedInvokersNames())
                .build();
    }
}


