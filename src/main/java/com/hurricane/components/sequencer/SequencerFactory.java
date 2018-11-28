package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.definition.SequencerDefinition;
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

    public Sequencer<T> create(final SequencerDefinition<T> definition) {
        final List<StepInvoker> invokers = createInvokers(definition);
        validateArtifacts(invokers, definition);
        return createSequencer(invokers, definition);
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

    //TODO refactor method / extract as a separate class
    private void validateArtifacts(final List<StepInvoker> invokers, final SequencerDefinition<T> definition) {
        final Map<String, ArtifactDefinition> availableArtifacts = new HashMap<>();
        definition.getInitial().asArtifactDefinition().ifPresent(initialDefinition -> availableArtifacts.put(initialDefinition.getName(), initialDefinition));
        for (StepInvoker invoker : invokers) {
            final List<ArtifactDefinition> consumedArtifacts = invoker.consumedArtifacts();
            for (ArtifactDefinition consumedArtifact : consumedArtifacts) {
                final Optional<ArtifactDefinition> availableArtifact = Optional.ofNullable(availableArtifacts.get(consumedArtifact.getName()));
                if (availableArtifact.isPresent()) {
                    if (!consumedArtifact.isCompatibleWith(availableArtifact.get())) {
                        //TODO create appropriate exception
                        throw new IllegalArgumentException("Wrong type / incompatible");
                    }
                } else {
                    //TODO create appropriate exception
                    throw new IllegalArgumentException("Wrong type");
                }
            }
            invoker.producedArtifact().ifPresent(producedDefinition -> availableArtifacts.put(producedDefinition.getName(), producedDefinition));
        }
    }

    private Sequencer<T> createSequencer(final List<StepInvoker> invokers, final SequencerDefinition<T> definition) {
        return Sequencer.of(invokers,
                definition.getExceptionHandler(),
                ContextInitalPopulator.of(definition.getInitial())
        );
    }
}