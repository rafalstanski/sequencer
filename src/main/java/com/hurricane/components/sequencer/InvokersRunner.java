package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.exception.ErrorReactionException;
import com.hurricane.components.sequencer.invoker.InvokerContext;
import com.hurricane.components.sequencer.invoker.StepInvoker;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor(staticName = "basedOn")
class InvokersRunner {
    private final List<StepInvoker> invokers;
    private final ExceptionHandler exceptionHandler;
    private final List<String> executedStepsNames = new ArrayList<>();
    private final List<StepInvokeError> errors = new ArrayList<>();
    private boolean shouldContinue = true;

    public SequencerResult run(final InvokerContext context) {
        final Iterator<StepInvoker> invokerIterator = invokers.iterator();
        do {
            final StepInvoker invoker = invokerIterator.next();
            executeSingleInvoker(invoker, context);
        } while (shouldContinue(invokerIterator));
        return createResult(context);
    }

    private void executeSingleInvoker(final StepInvoker invoker, final InvokerContext context) {
        executedStepsNames.add(invoker.getName());
        try {
            invoker.invoke(context);
        } catch (final Throwable e) {
            react(invoker, e);
        }
    }

    private boolean shouldContinue(final Iterator<StepInvoker> invokerIterator) {
        return shouldContinue && invokerIterator.hasNext();
    }

    private void react(final StepInvoker invoker, final Throwable cause) {
        if (nonRecoverable(cause)) {
            throw (Error) cause;
        } else {
            final StepInvokeError invokeError = StepInvokeError.of(invoker.getName(), cause);
            errors.add(invokeError);
            final Reaction reaction = exceptionHandler.handle(invokeError);
            handleReaction(reaction, invokeError);
        }
    }

    private boolean nonRecoverable(final Throwable cause) {
        // inspired by vavr library's Try class. Those exceptions are considered to be fatal/non-recoverable
        return cause instanceof LinkageError || cause instanceof ThreadDeath || cause instanceof VirtualMachineError;
    }

    private void handleReaction(final Reaction reaction, final StepInvokeError invokeError) {
        if (reaction == Reaction.INTERRUPT) {
            shouldContinue = false;
        } else //noinspection StatementWithEmptyBody
            if (reaction == Reaction.IGNORE) {
            //ignoring exception
        } else {
            throw ErrorReactionException.unsupportedReaction(reaction, invokeError);
        }
    }

    private SequencerResult createResult(final InvokerContext context) {
        return SequencerResult.builder()
                .steps(extractStepsNames())
                .executedSteps(executedStepsNames)
                .artifacts(context.getArtifacts().values())
                .errors(errors)
                .build();
    }

    private List<String> extractStepsNames() {
        return invokers.stream()
                .map(StepInvoker::getName)
                .collect(toList());
    }
}
