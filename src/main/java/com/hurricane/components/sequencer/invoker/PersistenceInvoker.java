package com.hurricane.components.sequencer.invoker;

import com.hurricane.components.sequencer.Artifact;
import com.hurricane.components.sequencer.ArtifactDefinition;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    public InvokeResult invoke(final InvokerContext context) {
        final List<Artifact> artifacts = extractRequiredArtifacts(context);
        final InvokeResult result = processingMethod.invoke(artifacts);
        storeReturnedValue(context, result);
        return result;
    }

    private List<Artifact> extractRequiredArtifacts(final InvokerContext context) {
        return consumedArtifactsDefinitions.stream()
                .map(artifactDefinition -> context.take(artifactDefinition.getName()))
                .collect(Collectors.toList());
    }

    private void storeReturnedValue(final InvokerContext context, InvokeResult result) {
        if (result.isSuccess()) {
            final Artifact producedArtifact = Artifact.of(producedArtifactDefinition, result.getResult());
            context.store(producedArtifact);
        }
    }

    @Override
    public List<ArtifactDefinition> consumedArtifacts() {
        return Collections.unmodifiableList(consumedArtifactsDefinitions);
    }

    @Override
    public Optional<ArtifactDefinition> producedArtifact() {
        return Optional.of(producedArtifactDefinition);
    }
}
