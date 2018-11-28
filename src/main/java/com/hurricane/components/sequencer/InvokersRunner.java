package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.exception.ErrorReactionException;
import com.hurricane.components.sequencer.invoker.InvokeResult;
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
        final InvokeResult invokeResult = invoker.invoke(context);
        if (invokeResult.isFailure()) {
            reactToFailure(invoker, invokeResult);
        }
    }

    private void reactToFailure(final StepInvoker invoker, final InvokeResult invokeResult) {
        final StepInvokeError invokeError = StepInvokeError.of(invoker.getName(), invokeResult.getCause());
        errors.add(invokeError);
        final Reaction reaction = exceptionHandler.handle(invokeError);
        handleReaction(reaction, invokeError);
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

    private boolean shouldContinue(final Iterator<StepInvoker> invokerIterator) {
        return shouldContinue && invokerIterator.hasNext();
    }

    private SequencerResult createResult(final InvokerContext context) {
        return SequencerResult.builder()
                .steps(extractStepsNames())
                .executedSteps(executedStepsNames)
                .artifacts(context.producedArtifacts())
                .errors(errors)
                .build();
    }

    private List<String> extractStepsNames() {
        return invokers.stream()
                .map(StepInvoker::getName)
                .collect(toList());
    }
}
