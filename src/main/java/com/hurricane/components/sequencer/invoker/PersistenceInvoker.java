package com.hurricane.components.sequencer.invoker;

import com.hurricane.components.sequencer.Artifact;
import com.hurricane.components.sequencer.ArtifactDefinition;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class PersistenceInvoker implements StepInvoker {
    private final String name;
    private final ProcessingMethod processingMethod;
    private final List<ArtifactDefinition> consumedArtifactsDefinitions;
    private final ArtifactDefinition producedArtifactDefinition;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void invoke(final InvokerContext context) {
        final List<Artifact> artifacts = extractRequiredArtifacts(context);
        final Object returnValue = processingMethod.invoke(artifacts);
        storeReturnedValue(context, returnValue);
    }

    private List<Artifact> extractRequiredArtifacts(final InvokerContext context) {
        //TODO add validation of extracted artifact: is type assignable, is not null
        return consumedArtifactsDefinitions.stream()
                .map(artifactDefinition -> context.take(artifactDefinition.getName()))
                .collect(Collectors.toList());
    }

    private void storeReturnedValue(final InvokerContext context, final Object returnValue) {
        //TODO add validation of returnValue
        final Artifact producedArtifact = Artifact.of(producedArtifactDefinition, returnValue);
        context.store(producedArtifact);
    }
}
