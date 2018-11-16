package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.definition.SequencerDefinition;
import com.hurricane.components.sequencer.invoker.InitialStepInvoker;
import com.hurricane.components.sequencer.invoker.StepInvoker;
import com.hurricane.components.sequencer.invoker.builder.InitialStepInvokerBuilder;
import com.hurricane.components.sequencer.invoker.builder.StepInvokerBuilder;
import com.hurricane.components.sequencer.step.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class SequencerFactory<T> {
    private final StepInvokerBuilder invokerBuilder = new StepInvokerBuilder();
    private final InitialStepInvokerBuilder initialStepInvokerBuilder = new InitialStepInvokerBuilder();

    public Sequencer<T> create(final SequencerDefinition<T> definition) {
        final List<StepInvoker> invokers = createInvokers(definition);
        return Sequencer.of(invokers, definition.getExceptionHandler());
    }

    private List<StepInvoker> createInvokers(final SequencerDefinition<T> definition) {
        final List<StepInvoker> invokers = new ArrayList<>();
        addInitialStepIfNecessary(definition, invokers);
        for (final Class<? extends Step> stepClass : definition.getStepsDefinitions()) {
            final Step step = definition.getStepFactory().create(stepClass);
            final StepInvoker invoker = invokerBuilder.build(step);
            invokers.add(invoker);
        }
        return invokers;
    }

    private void addInitialStepIfNecessary(final SequencerDefinition<T> definition, final List<StepInvoker> invokers) {
        final Optional<InitialStepInvoker> initialInvoker = initialStepInvokerBuilder.build(definition.getInitial());
        initialInvoker.ifPresent(invokers::add);
    }
}
