package com.hurricane.components.sequencer.configure.invoker;

import com.hurricane.components.sequencer.configure.annotations.Artifact;
import com.hurricane.components.sequencer.exception.ConsumedArtifactsException;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.Parameter;

public class ArtifactNameExtractor {
    public String extract(final Parameter parameter) {
        Validate.notNull(parameter, "Unable to extract artifact name from null parameter");
        if (byAnnotation(parameter)) {
            return fromAnnotation(parameter);
        } else if (byCompiledName(parameter)) {
            return fromCompiledName(parameter);
        } else {
            throw unableToExtract(parameter);
        }
    }

    private boolean byAnnotation(final Parameter parameter) {
        return parameter.isAnnotationPresent(Artifact.class);
    }

    private String fromAnnotation(final Parameter parameter) {
        final Artifact artifact = parameter.getAnnotation(Artifact.class);
        return artifact.value();
    }

    private boolean byCompiledName(final Parameter parameter) {
        return parameter.isNamePresent();
    }

    private String fromCompiledName(final Parameter parameter) {
        return parameter.getName();
    }

    private ConsumedArtifactsException unableToExtract(final Parameter parameter) {
        return ConsumedArtifactsException.unableToDetermineArtifactName(parameter);
    }
}
