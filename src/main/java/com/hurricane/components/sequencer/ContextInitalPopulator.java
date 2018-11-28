package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.invoker.InvokerContext;
import lombok.Data;

import java.util.Optional;

@Data(staticConstructor = "of")
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
}
