package com.hurricane.components.sequencer.configure.validation;

import com.hurricane.components.sequencer.runtime.ArtifactDefinition;

import java.util.*;

class ValidationContext {
    private final Map<String, ArtifactDefinition> artifactsDefinitions = new HashMap<>();

    public Optional<ArtifactDefinition> take(final String name) {
        return Optional.ofNullable(artifactsDefinitions.get(name));
    }

    public void store(final ArtifactDefinition definition) {
        artifactsDefinitions.put(definition.getName(), definition);
    }

    public Collection<ArtifactDefinition> availableArtifacts() {
        return Collections.unmodifiableCollection(artifactsDefinitions.values());
    }
}
