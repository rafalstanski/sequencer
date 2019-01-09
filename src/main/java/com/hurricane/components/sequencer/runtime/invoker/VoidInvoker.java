package com.hurricane.components.sequencer.runtime.invoker;

import com.hurricane.components.sequencer.runtime.Artifact;
import com.hurricane.components.sequencer.runtime.ArtifactDefinition;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
@Data
public class VoidInvoker implements StepInvoker {
    private final String name;
    private final ProcessingMethod processingMethod;
    private final List<ArtifactDefinition> consumedArtifactsDefinitions;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public InvokeResult invoke(final InvokerContext context) {
        final List<Artifact> artifacts = extractRequiredArtifacts(context);
        return processingMethod.invoke(artifacts);
    }

    private List<Artifact> extractRequiredArtifacts(final InvokerContext context) {
        return consumedArtifactsDefinitions.stream()
                .map(artifactDefinition -> context.take(artifactDefinition.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ArtifactDefinition> consumedArtifacts() {
        return Collections.unmodifiableList(consumedArtifactsDefinitions);
    }

    @Override
    public Optional<ArtifactDefinition> producedArtifact() {
        return Optional.empty();
    }
}
