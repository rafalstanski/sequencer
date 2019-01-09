package com.hurricane.components.sequencer.configure.invoker;

import com.hurricane.components.sequencer.runtime.ArtifactDefinition;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

class ConsumedArtifactsFinder {
    private final ArtifactNameExtractor nameExtractor = new ArtifactNameExtractor();

    public List<ArtifactDefinition> find(final Method stepMethod) {
        Validate.notNull(stepMethod, "Step's processing method is required shouldn't be null");
        return Arrays.stream(stepMethod.getParameters())
                .map(this::createArtifactDefinition)
                .collect(toList());
    }

    private ArtifactDefinition createArtifactDefinition(final Parameter parameter) {
        return ArtifactDefinition.of(
                extractName(parameter),
                parameter.getType()
        );
    }

    private String extractName(final Parameter parameter) {
        return nameExtractor.extract(parameter);
    }
}
