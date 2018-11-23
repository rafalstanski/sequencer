package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.invoker.InvokerContext;
import lombok.Data;

@Data(staticConstructor = "of")
public class ContextInitalPopulator<T> {
    private final Initial<T> initial;

    public void populate(final InvokerContext invokerContext, final T initialValue) {
        if (initial.expectInput()) {
            final Artifact artifact = createArtifact(initialValue);
            invokerContext.store(artifact);
        }
    }

    private Artifact createArtifact(final T value) {
        final ArtifactDefinition definition = ArtifactDefinition.of(initial.getArtifactName(), initial.getInitialInstanceClass());
        return Artifact.of(definition, value);
    }
}
