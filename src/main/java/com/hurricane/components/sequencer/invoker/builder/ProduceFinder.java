package com.hurricane.components.sequencer.invoker.builder;

import com.hurricane.components.sequencer.annotations.Artifact;
import lombok.AllArgsConstructor;

import java.lang.reflect.Method;
import java.util.Optional;

@AllArgsConstructor(staticName = "of")
class ProduceFinder {
    final Method processingMethod;

    public Optional<ProducerDefinition> find() {
        if (processingMethod.isAnnotationPresent(Artifact.class)) {
            final ProducerDefinition producerDefinition = ProducerDefinition.builder()
                    .name(extractName())
                    .build();
            return Optional.of(producerDefinition);
        } else {
            return Optional.empty();
        }
    }

    private String extractName() {
        final Artifact artifact = processingMethod.getAnnotation(Artifact.class);
        return artifact.value();
    }
}
