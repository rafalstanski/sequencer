package com.hurricane.components.sequencer.invoker;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data(staticConstructor = "of")
public class InvokerContext {
    private final Object initial;
    private Map<String, Object> artifacts = new HashMap<>();

    public Object get(final String artifactName) {
        return artifacts.get(artifactName);
    }

    public void put(final String artifactName, final Object value) {
        artifacts.put(artifactName, value);
    }
}
