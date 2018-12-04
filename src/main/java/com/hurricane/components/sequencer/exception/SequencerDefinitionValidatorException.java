package com.hurricane.components.sequencer.exception;

import com.hurricane.components.sequencer.ArtifactDefinition;
import com.hurricane.components.sequencer.invoker.StepInvoker;

import java.util.Collection;

public class SequencerDefinitionValidatorException extends SequencerException {
    private SequencerDefinitionValidatorException(final String message) {
        super(message);
    }

    public static SequencerDefinitionValidatorException incompatibleType(final StepInvoker invoker,
                                                                         final ArtifactDefinition consumedArtifact,
                                                                         final ArtifactDefinition availableArtifact) {
        return new SequencerDefinitionValidatorException(createIncompatibleTypeMessage(invoker, consumedArtifact, availableArtifact));
    }

    public static SequencerDefinitionValidatorException unavailableArtifact(final StepInvoker invoker,
                                                                            final ArtifactDefinition consumedArtifact,
                                                                            final Collection<ArtifactDefinition> artifactDefinitions) {
        return new SequencerDefinitionValidatorException(createUnavailableArtifactMessage(invoker, consumedArtifact, artifactDefinitions));
    }

    private static String createIncompatibleTypeMessage(final StepInvoker invoker,
                                                 final ArtifactDefinition consumedArtifact,
                                                 final ArtifactDefinition availableArtifact) {
        return "Invoker '" +
                invoker.getName() +
                "' (of a class: " +
                invoker.getClass() +
                ") need to consume artifact defined as: " +
                consumedArtifact + ". " +
                "Available artifact is of a wrong type: " +
                availableArtifact;
    }

    private static String createUnavailableArtifactMessage(final StepInvoker invoker,
                                                           final ArtifactDefinition consumedArtifact,
                                                           final Collection<ArtifactDefinition> artifactDefinitions) {
        return "Invoker '" +
                invoker.getName() +
                "' (of a class: " +
                invoker.getClass() +
                ") expects artifact defined as: " +
                consumedArtifact + ". " +
                "Available artifact are: " +
                artifactDefinitions;
    }
}
