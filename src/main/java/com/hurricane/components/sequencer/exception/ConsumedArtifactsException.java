package com.hurricane.components.sequencer.exception;

import com.hurricane.components.sequencer.runtime.invoker.ProcessingMethod;

import java.lang.reflect.Parameter;

public class ConsumedArtifactsException extends SequencerException {
    private ConsumedArtifactsException(String message) {
        super(message);
    }

    public static ConsumedArtifactsException unableToDetermineArtifactName(
            final ProcessingMethod processingMethod,
            final Parameter parameter) {
        return new ConsumedArtifactsException(
                createUnableToDetermineArtifactNameMessage(processingMethod, parameter));
    }

    private static String createUnableToDetermineArtifactNameMessage(
            final ProcessingMethod processingMethod,
            final Parameter parameter) {
        return "Unable to extract artifact name based on step's processing method's (/" +
                processingMethod.getProcessMethod() + "/) parameter: /" +
                parameter + "/. " +
                "You need to compile with -parameters option or use @Artifact annotation";
    }
}