package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.definition.SequencerBuildConfiguration;
import com.hurricane.components.sequencer.definition.SequencerDefinition;
import com.hurricane.components.sequencer.definition.validation.SequencerDefinitionValidator;
import com.hurricane.components.sequencer.invoker.StepInvoker;
import com.hurricane.components.sequencer.invoker.builder.StepInvokerBuilder;
import com.hurricane.components.sequencer.step.Step;
import com.hurricane.components.sequencer.step.StepDefinition;
import com.hurricane.components.sequencer.step.StepFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

class SequencerFactory<T> {
    private final StepInvokerBuilder invokerBuilder = new StepInvokerBuilder();
    private final SequencerDefinitionValidator validator = new SequencerDefinitionValidator();

    public Sequencer<T> create(final SequencerBuildConfiguration<T> configuration) {
        final SequencerDefinition definition = createDefinition(configuration);
        validateDefinition(definition);
        return createSequencer(definition);
    }

    private SequencerDefinition createDefinition(final SequencerBuildConfiguration<T> configuration) {
        final List<StepInvoker> invokers = createInvokers(configuration);
        return SequencerDefinition.builder()
                .invokers(invokers)
                .exceptionHandler(configuration.getExceptionHandler())
                .populator(ContextInitalPopulator.of(configuration.getInitial()))
                .build();
    }

    private List<StepInvoker> createInvokers(final SequencerBuildConfiguration<T> configuration) {
        return configuration.getStepsDefinitions().stream()
                .map(stepDefinition -> createInvoker(stepDefinition, configuration.getStepFactory()))
                .collect(toList());
    }

    private StepInvoker createInvoker(final StepDefinition stepDefinition, final StepFactory stepFactory) {
        final Step step = stepFactory.create(stepDefinition);
        return invokerBuilder.build(step);
    }

    private void validateDefinition(final SequencerDefinition definition) {
        validator.validate(definition);
    }

    private Sequencer<T> createSequencer(final SequencerDefinition definition) {
        return Sequencer.of(definition);
    }
}