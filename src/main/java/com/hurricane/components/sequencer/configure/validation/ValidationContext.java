package com.hurricane.components.sequencer.configure.validation;

import com.hurricane.components.sequencer.runtime.ArtifactDefinition;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.unmodifiableCollection;

class ValidationContext {
    private final Map<String, ArtifactDefinition> artifactsDefinitions = new HashMap<>();

    public Optional<ArtifactDefinition> take(final String name) {
        return Optional.ofNullable(artifactsDefinitions.get(name));
    }

    public void store(final ArtifactDefinition definition) {
        artifactsDefinitions.put(definition.getName(), definition);
    }

    public Collection<ArtifactDefinition> availableArtifacts() {
        return unmodifiableCollection(artifactsDefinitions.values());
    }
}
