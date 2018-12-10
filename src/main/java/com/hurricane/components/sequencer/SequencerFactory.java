package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.definition.SequencerBuildConfiguration;
import com.hurricane.components.sequencer.definition.SequencerDefinition;
import com.hurricane.components.sequencer.definition.validation.SequencerDefinitionValidator;
import com.hurricane.components.sequencer.invoker.StepInvoker;
import com.hurricane.components.sequencer.invoker.builder.StepInvokerBuilder;
import com.hurricane.components.sequencer.step.Step;
import com.hurricane.components.sequencer.step.StepFactory;

import java.util.List;

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
        return SequencerDefinition.builder()
                .invokers(createInvokers(configuration))
                .exceptionHandler(configuration.getExceptionHandler())
                .populator(ContextInitalPopulator.of(configuration.getInitial()))
                .build();
    }

    private void validateDefinition(final SequencerDefinition definition) {
        validator.validate(definition);
    }

    private List<StepInvoker> createInvokers(final SequencerBuildConfiguration<T> configuration) {
        final List<Step> steps = createSteps(configuration);
        return steps.stream()
                .map(this.invokerBuilder::build)
                .collect(toList());
    }

    private List<Step> createSteps(final SequencerBuildConfiguration<T> configuration) {
        final StepFactory stepFactory = configuration.getStepFactory();
        return configuration.getStepsDefinitions().stream()
                .map(stepFactory::create)
                .collect(toList());
    }

    private Sequencer<T> createSequencer(final SequencerDefinition definition) {
        return Sequencer.of(definition);
    }
}