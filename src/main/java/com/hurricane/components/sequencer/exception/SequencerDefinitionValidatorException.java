package com.hurricane.components.sequencer.exception;

import com.hurricane.components.sequencer.ArtifactDefinition;
import com.hurricane.components.sequencer.invoker.StepInvoker;

import java.util.Collection;

public class SequencerDefinitionValidatorException extends SequencerException {
    public SequencerDefinitionValidatorException(final String message) {
        super(message);
    }


    public static SequencerDefinitionValidatorException incompatibleType(final StepInvoker invoker,
                                                                         final ArtifactDefinition consumedArtifact,
                                                                         final ArtifactDefinition availableArtifact) {
        //TODO create better message
        return new SequencerDefinitionValidatorException("Wrong type / incompatible");
    }

    public static SequencerDefinitionValidatorException unavailableArtifact(final StepInvoker invoker,
                                                                            final ArtifactDefinition consumedArtifact,
                                                                            final Collection<ArtifactDefinition> artifactDefinitions) {
        //TODO create better message
        return new SequencerDefinitionValidatorException("Wrong type");
    }
}
