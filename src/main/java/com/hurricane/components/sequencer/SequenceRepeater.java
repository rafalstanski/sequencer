package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.invoker.SequenceInvoker;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class SequenceRepeater {
    private final Iterator<SequenceInvoker> invokerIterator;
    private final ExceptionHandler exceptionHandler;
    private boolean shouldContinue = true;
    @Getter
    private final List<String> providedInvokersNames = new ArrayList<>();
    @Getter
    private final List<SequenceInvokeError> errors = new ArrayList<>();

    static SequenceRepeater basedOn(final Collection<SequenceInvoker> invokers, final ExceptionHandler exceptionHandler) {
        return new SequenceRepeater(new ArrayList<>(invokers).iterator(), exceptionHandler);
    }

    public boolean shouldContinue() {
        return shouldContinue && invokerIterator.hasNext();
    }

    public SequenceInvoker provide() {
        final SequenceInvoker invoker = invokerIterator.next();
        providedInvokersNames.add(invoker.getName());
        return invoker;
    }

    public void react(final RuntimeException e) {
        final SequenceInvokeError invokeError = SequenceInvokeError.of(lastProvidedInvokerName(), e);
        errors.add(invokeError);
        final Reaction reaction = exceptionHandler.handle(e);
        if (reaction == Reaction.RETHROW) {
            throw e;
        } else if (reaction == Reaction.INTERRUPT) {
            shouldContinue = false;
        }
    }

    private String lastProvidedInvokerName() {
        return providedInvokersNames.get(providedInvokersNames.size() - 1);
    }
}
