package com.hurricane.components.sequencer.exception;

import java.lang.reflect.Parameter;

public class ConsumedArtifactsException extends SequencerException {
    private ConsumedArtifactsException(String message) {
        super(message);
    }

    public static ConsumedArtifactsException unableToDetermineArtifactName(final Parameter parameter) {
        return new ConsumedArtifactsException(createUnableToDetermineArtifactNameMessage(parameter));
    }

    private static String createUnableToDetermineArtifactNameMessage(final Parameter parameter) {
        return "Unable to extract artifact name based on step's processing method's (/" +
                parameter.getDeclaringExecutable() + "/) parameter: /" +
                parameter + "/. " +
                "You need to compile with -parameters option or use @Artifact annotation";
    }
}