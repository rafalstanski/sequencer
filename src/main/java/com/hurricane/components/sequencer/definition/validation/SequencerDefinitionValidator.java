package com.hurricane.components.sequencer.definition.validation;

import com.hurricane.components.sequencer.ArtifactDefinition;
import com.hurricane.components.sequencer.definition.SequencerDefinition;
import com.hurricane.components.sequencer.exception.SequencerDefinitionValidatorException;
import com.hurricane.components.sequencer.invoker.StepInvoker;

import java.util.List;
import java.util.Optional;

public class SequencerDefinitionValidator {
    public void validate(final SequencerDefinition definition) {
        final ValidationContext context = createInitialContext(definition);
        for (final StepInvoker invoker : definition.getInvokers()) {
            validateInvoker(context, invoker);
        }
    }

    private void validateInvoker(final ValidationContext context, final StepInvoker invoker) {
        final List<ArtifactDefinition> consumedArtifacts = invoker.consumedArtifacts();
        for (final ArtifactDefinition consumedArtifact : consumedArtifacts) {
            final Optional<ArtifactDefinition> availableArtifact = context.take(consumedArtifact.getName());
            if (availableArtifact.isPresent()) {
                if (!consumedArtifact.isCompatibleWith(availableArtifact.get())) {
                    throw SequencerDefinitionValidatorException.incompatibleType(invoker, consumedArtifact, availableArtifact.get());
                }
            } else {
                throw SequencerDefinitionValidatorException.unavailableArtifact(invoker, consumedArtifact, context.availableArtifacts());
            }
        }
        invoker.producedArtifact().ifPresent(context::store);
    }

    private ValidationContext createInitialContext(final SequencerDefinition definition) {
        final ValidationContext context = new ValidationContext();
        final Optional<ArtifactDefinition> initialArtifactDefinition = definition.getPopulator()
                .initialArtifactDefinition();
        initialArtifactDefinition.ifPresent(context::store);
        return context;
    }
}