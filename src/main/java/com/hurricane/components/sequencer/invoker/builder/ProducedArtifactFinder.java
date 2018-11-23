package com.hurricane.components.sequencer.invoker.builder;

import com.hurricane.components.sequencer.ArtifactDefinition;
import com.hurricane.components.sequencer.annotations.Artifact;
import com.hurricane.components.sequencer.invoker.ProcessingMethod;
import lombok.AllArgsConstructor;

import java.lang.reflect.Method;
import java.util.Optional;

@AllArgsConstructor(staticName = "of")
class ProducedArtifactFinder {
    final ProcessingMethod processingMethod;

    public Optional<ArtifactDefinition> find() {
        if (methodProducesArtifact()) {
            return Optional.of(createProducedArtifactDefinition());
        } else {
            return Optional.empty();
        }
    }

    private boolean methodProducesArtifact() {
        final Method processMethod = processingMethod.getProcessMethod();
        return processMethod.isAnnotationPresent(Artifact.class);
    }

    private ArtifactDefinition createProducedArtifactDefinition() {
        final Method processMethod = processingMethod.getProcessMethod();
        return ArtifactDefinition.of(extractName(processMethod), processMethod.getReturnType());
    }

    private String extractName(final Method processMethod) {
        final Artifact artifact = processMethod.getAnnotation(Artifact.class);
        return artifact.value();
    }
}
