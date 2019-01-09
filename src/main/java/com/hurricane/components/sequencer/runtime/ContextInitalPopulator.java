package com.hurricane.components.sequencer.runtime;

import com.hurricane.components.sequencer.runtime.invoker.InvokerContext;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Optional;

@RequiredArgsConstructor(staticName = "of")
@ToString
public class ContextInitalPopulator {
    private final Initial<?> initial;

    public void populate(final InvokerContext invokerContext, final Object initialValue) {
        final Optional<Artifact> artifact = createArtifact(initialValue);
        artifact.ifPresent(invokerContext::store);
    }

    private Optional<Artifact> createArtifact(final Object value) {
        final Optional<ArtifactDefinition> artifactDefinition = initial.asArtifactDefinition();
        return artifactDefinition.map(definition -> Artifact.of(definition, value));
    }

    public Optional<ArtifactDefinition> initialArtifactDefinition() {
        return initial.asArtifactDefinition();
    }
}
