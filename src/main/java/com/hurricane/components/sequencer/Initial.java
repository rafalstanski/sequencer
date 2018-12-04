package com.hurricane.components.sequencer;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Initial<T> {
    private final String artifactName;
    private final Class<T> initialInstanceClass;

    public static Initial<Void> non() {
        return new Initial<>(StringUtils.EMPTY, Void.class);
    }

    public static <T> Initial<T> artifact(final String artifactName, final Class<T> type) {
        return new Initial<>(artifactName, type);
    }

    public Optional<ArtifactDefinition> asArtifactDefinition() {
        if (expectInput()) {
            return Optional.of(ArtifactDefinition.of(artifactName, initialInstanceClass));
        } else {
            return Optional.empty();
        }
    }

    public boolean expectInput() {
        return initialInstanceClass != Void.class;
    }
}
