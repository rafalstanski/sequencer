package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.invoker.InvokerContext;
import com.hurricane.components.sequencer.invoker.StepInvoker;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor(staticName = "basedOn")
class InvokersRunner {
    private final List<StepInvoker> invokers;
    private final ExceptionHandler exceptionHandler;
    private boolean shouldContinue = true;
    @Getter
    private final List<String> executedStepsNames = new ArrayList<>();
    @Getter
    private final List<StepInvokeError> errors = new ArrayList<>();

    public void run(final InvokerContext context) {
        final Iterator<StepInvoker> invokerIterator = invokers.iterator();
        do {
            final StepInvoker invoker = invokerIterator.next();
            execute(invoker, context);
        } while (shouldContinue(invokerIterator));
    }

    private void execute(final StepInvoker invoker, final InvokerContext context) {
        executedStepsNames.add(invoker.getName());
        try {
            invoker.invoke(context);
        } catch (final RuntimeException e) {
            react(invoker, e);
        }
    }

    private boolean shouldContinue(final Iterator<StepInvoker> invokerIterator) {
        return shouldContinue && invokerIterator.hasNext();
    }

    private void react(final StepInvoker invoker, final RuntimeException e) {
        final StepInvokeError invokeError = StepInvokeError.of(invoker.getName(), e);
        errors.add(invokeError);
        final Reaction reaction = exceptionHandler.handle(invokeError);
        handleReaction(reaction, invokeError);
    }

    private void handleReaction(final Reaction reaction, final StepInvokeError invokeError) {
        if (reaction == Reaction.RETHROW) {
            throw invokeError.getException();
        } else if (reaction == Reaction.INTERRUPT) {
            shouldContinue = false;
        } else //noinspection StatementWithEmptyBody
            if (reaction == Reaction.IGNORE) {
            //ignoring exception
        } else {
            //TODO add special exception
            throw new IllegalStateException("Exception handler provided unsupported reaction: " + reaction);
        }
    }
}
