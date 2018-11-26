package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.invoker.InvokerContext;
import lombok.Data;

@Data(staticConstructor = "of")
public class ContextInitalPopulator {
    private final Initial<?> initial;

    public void populate(final InvokerContext invokerContext, final Object initialValue) {
        if (initial.expectInput()) {
            final Artifact artifact = createArtifact(initialValue);
            invokerContext.store(artifact);
        }
    }

    private Artifact createArtifact(final Object value) {
        final ArtifactDefinition definition = ArtifactDefinition.of(initial.getArtifactName(), initial.getInitialInstanceClass());
        return Artifact.of(definition, value);
    }
}
