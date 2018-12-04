package com.hurricane.components.sequencer;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class ArtifactDefinition {
    private final String name;
    private final Class<?> supportedType;

    public static ArtifactDefinition of(final String name, final Class<?> supportedType) {
        Validate.notBlank(name, "Artifact's name shouldn't be null");
        Validate.notNull(supportedType, "Artifact's supported type shouldn't be null");
        return new ArtifactDefinition(name, supportedType);
    }

    public boolean isCompatibleWith(final ArtifactDefinition sourceDefinition) {
        Validate.notNull(sourceDefinition, "Checked artifact's definition shouldn't be null");
        return sourceDefinition.name.equals(name)
                && sourceDefinition.supportedType.isAssignableFrom(supportedType);
    }
}
