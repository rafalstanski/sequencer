package com.hurricane.components.sequencer.invoker.builder;

import com.hurricane.components.sequencer.annotations.Artifact;
import lombok.AllArgsConstructor;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(staticName = "of")
class ConsumeFinder {
    final Method processingMethod;

    public List<ConsumerDefinition> find() {
        final Parameter[] parameters = processingMethod.getParameters();
        return Arrays.stream(parameters)
                .map(this::createDefinition)
                .collect(Collectors.toList());
    }

    private ConsumerDefinition createDefinition(final Parameter parameter) {
        return ConsumerDefinition.builder()
                .name(extractName(parameter))
                .type(parameter.getType())
                .build();
    }

    private String extractName(final Parameter parameter) {
        if (parameter.isAnnotationPresent(Artifact.class)) {
            final Artifact artifact = parameter.getAnnotation(Artifact.class);
            return artifact.value();
        } else if (parameter.isNamePresent()) {
            return parameter.getName();
        } else {
            //TODO give correct exception
            throw new IllegalStateException("You need to compile with -parameters option or use @Artifact");
        }
    }
}
