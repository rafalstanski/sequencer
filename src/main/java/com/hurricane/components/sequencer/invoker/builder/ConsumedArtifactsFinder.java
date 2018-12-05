package com.hurricane.components.sequencer.invoker.builder;

import com.hurricane.components.sequencer.ArtifactDefinition;
import com.hurricane.components.sequencer.annotations.Artifact;
import com.hurricane.components.sequencer.exception.ConsumedArtifactsException;
import com.hurricane.components.sequencer.invoker.ProcessingMethod;
import lombok.AllArgsConstructor;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(staticName = "of")
class ConsumedArtifactsFinder {
    final ProcessingMethod processingMethod;

    public List<ArtifactDefinition> find() {
        final Method processMethod = processingMethod.getProcessMethod();
        final Parameter[] parameters = processMethod.getParameters();
        return Arrays.stream(parameters)
                .map(this::createArtifactDefinition)
                .collect(Collectors.toList());
    }

    private ArtifactDefinition createArtifactDefinition(final Parameter parameter) {
        return ArtifactDefinition.of(extractName(parameter), parameter.getType());
    }

    private String extractName(final Parameter parameter) {
        if (parameter.isAnnotationPresent(Artifact.class)) {
            final Artifact artifact = parameter.getAnnotation(Artifact.class);
            return artifact.value();
        } else if (parameter.isNamePresent()) {
            return parameter.getName();
        } else {
            throw ConsumedArtifactsException.unableToDetermineArtifactName(processingMethod, parameter);
        }
    }
}
