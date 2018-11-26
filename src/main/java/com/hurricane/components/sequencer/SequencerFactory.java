package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.definition.SequencerDefinition;
import com.hurricane.components.sequencer.invoker.StepInvoker;
import com.hurricane.components.sequencer.invoker.builder.StepInvokerBuilder;
import com.hurricane.components.sequencer.step.Step;
import com.hurricane.components.sequencer.step.StepDefinition;
import com.hurricane.components.sequencer.step.StepFactory;

import java.util.List;

import static java.util.stream.Collectors.toList;

class SequencerFactory<T> {
    private final StepInvokerBuilder invokerBuilder = new StepInvokerBuilder();

    public Sequencer<T> create(final SequencerDefinition<T> definition) {
        final List<StepInvoker> invokers = createInvokers(definition);
        return Sequencer.of(invokers, definition.getExceptionHandler(), ContextInitalPopulator.of(definition.getInitial()));
    }

    private List<StepInvoker> createInvokers(final SequencerDefinition<T> definition) {
        return definition.getStepsDefinitions().stream()
                .map(stepDefinition -> createInvoker(stepDefinition, definition.getStepFactory()))
                .collect(toList());
    }

    private StepInvoker createInvoker(final StepDefinition stepDefinition, final StepFactory stepFactory) {
        final Step step = stepFactory.create(stepDefinition);
        return invokerBuilder.build(step);
    }
}