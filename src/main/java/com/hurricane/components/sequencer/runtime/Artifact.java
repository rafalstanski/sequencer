package com.hurricane.components.sequencer.runtime;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class Artifact {
    private final ArtifactDefinition definition;
    private final Object value;

    public static Artifact of(final ArtifactDefinition definition, final Object value) {
        Validate.notNull(definition, "Artifact's definition shouldn't be null");
        return new Artifact(definition, value);
    }

    public String name() {
        return definition.getName();
    }
}
