package com.hurricane.components.sequencer.invoker.builder;

import com.hurricane.components.sequencer.ArtifactDefinition;
import com.hurricane.components.sequencer.invoker.ProcessingMethod;
import com.hurricane.components.sequencer.step.Step;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Builder
@Data
class InvokerConfiguration {
    private final Step step;
    private final String stepName;
    private final ProcessingMethod processingMethod;
    private final List<ArtifactDefinition> consumedArtifact;
    private final ArtifactDefinition producedArtifact;

    public boolean producedArtifactDefined() {
        return Objects.nonNull(producedArtifact);
    }
}
