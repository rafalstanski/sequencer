package com.hurricane.components.sequencer.runtime.invoker;

import com.hurricane.components.sequencer.runtime.Artifact;
import lombok.ToString;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ToString
public class InvokerContext {
    private final Map<String, Artifact> artifacts = new HashMap<>();

    public Artifact take(final String artifactName) {
        return artifacts.get(artifactName);
    }

    public void store(final Artifact artifact) {
        artifacts.put(artifact.name(), artifact);
    }

    public Collection<Artifact> producedArtifacts() {
        return Collections.unmodifiableCollection(artifacts.values());
    }
}
