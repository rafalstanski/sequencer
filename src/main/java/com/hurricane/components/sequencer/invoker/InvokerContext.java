package com.hurricane.components.sequencer.invoker;

import com.hurricane.components.sequencer.Artifact;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class InvokerContext {
    //TODO this should not be visible outside class
    private final Map<String, Artifact> artifacts = new HashMap<>();

    public Artifact take(final String artifactName) {
        return artifacts.get(artifactName);
    }

    public void store(final Artifact artifact) {
        artifacts.put(artifact.name(), artifact);
    }
}
