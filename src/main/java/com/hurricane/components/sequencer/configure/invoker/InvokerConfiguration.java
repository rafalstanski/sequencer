package com.hurricane.components.sequencer.configure.invoker;

import com.hurricane.components.sequencer.runtime.ArtifactDefinition;
import com.hurricane.components.sequencer.runtime.Step;
import com.hurricane.components.sequencer.runtime.invoker.ProcessingMethod;
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

    public boolean producesArtifact() {
        return Objects.nonNull(producedArtifact);
    }
}
