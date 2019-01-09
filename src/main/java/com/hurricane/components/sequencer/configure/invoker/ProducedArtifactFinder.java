package com.hurricane.components.sequencer.configure.invoker;

import com.hurricane.components.sequencer.configure.annotations.Artifact;
import com.hurricane.components.sequencer.runtime.ArtifactDefinition;

import java.lang.reflect.Method;
import java.util.Optional;

class ProducedArtifactFinder {
    public Optional<ArtifactDefinition> find(final Method processMethod) {
        if (methodProducesArtifact(processMethod)) {
            return Optional.of(createProducedArtifactDefinition(processMethod));
        } else {
            return Optional.empty();
        }
    }

    private boolean methodProducesArtifact(final Method processMethod) {
        return processMethod.isAnnotationPresent(Artifact.class);
    }

    private ArtifactDefinition createProducedArtifactDefinition(final Method processMethod) {
        return ArtifactDefinition.of(
                extractName(processMethod),
                processMethod.getReturnType()
        );
    }

    private String extractName(final Method processMethod) {
        final Artifact artifact = processMethod.getAnnotation(Artifact.class);
        return artifact.value();
    }
}
