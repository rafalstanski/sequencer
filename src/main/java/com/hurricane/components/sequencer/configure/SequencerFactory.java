package com.hurricane.components.sequencer.configure;

import com.hurricane.components.sequencer.configure.invoker.StepInvokerBuilder;
import com.hurricane.components.sequencer.configure.step.StepFactory;
import com.hurricane.components.sequencer.configure.validation.SequencerDefinitionValidator;
import com.hurricane.components.sequencer.runtime.ContextInitalPopulator;
import com.hurricane.components.sequencer.runtime.Sequencer;
import com.hurricane.components.sequencer.runtime.SequencerContract;
import com.hurricane.components.sequencer.runtime.Step;
import com.hurricane.components.sequencer.runtime.invoker.StepInvoker;
import org.apache.commons.lang3.Validate;

import java.util.List;

import static java.util.stream.Collectors.toList;

class SequencerFactory<T> {
    private final StepInvokerBuilder invokerBuilder = new StepInvokerBuilder();
    private final SequencerDefinitionValidator validator = new SequencerDefinitionValidator();

    public Sequencer<T> create(final SequencerBuildConfiguration<T> configuration) {
        Validate.notNull(configuration, "Build configuration shouldn't be null");
        final SequencerContract definition = createDefinition(configuration);
        validateDefinition(definition);
        return createSequencer(definition);
    }

    private SequencerContract createDefinition(final SequencerBuildConfiguration<T> configuration) {
        return SequencerContract.builder()
                .invokers(createInvokers(configuration))
                .exceptionHandler(configuration.getExceptionHandler())
                .populator(ContextInitalPopulator.of(configuration.getInitial()))
                .build();
    }

    private void validateDefinition(final SequencerContract definition) {
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

    private Sequencer<T> createSequencer(final SequencerContract definition) {
        return Sequencer.of(definition);
    }
}