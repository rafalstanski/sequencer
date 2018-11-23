package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.definition.SequencerDefinition;
import com.hurricane.components.sequencer.invoker.StepInvoker;
import com.hurricane.components.sequencer.invoker.builder.StepInvokerBuilder;
import com.hurricane.components.sequencer.step.Step;
import com.hurricane.components.sequencer.step.StepDefinition;

import java.util.ArrayList;
import java.util.List;

class SequencerFactory<T> {
    private final StepInvokerBuilder invokerBuilder = new StepInvokerBuilder();

    public Sequencer<T> create(final SequencerDefinition<T> definition) {
        final List<StepInvoker> invokers = createInvokers(definition);
        return Sequencer.of(invokers, definition.getExceptionHandler(), ContextInitalPopulator.of(definition.getInitial()));
    }

    private List<StepInvoker> createInvokers(final SequencerDefinition<T> definition) {
        final List<StepInvoker> invokers = new ArrayList<>();
        for (final StepDefinition stepDefinition : definition.getStepsDefinitions()) {
            final Step step = definition.getStepFactory().create(stepDefinition);
            final StepInvoker invoker = invokerBuilder.build(step);
            invokers.add(invoker);
        }
        return invokers;
    }
}
