package com.hurricane.components.sequencer.configure.invoker;

import com.hurricane.components.sequencer.runtime.ArtifactDefinition;
import com.hurricane.components.sequencer.runtime.Step;
import com.hurricane.components.sequencer.runtime.invoker.ProcessingMethod;
import lombok.var;

import java.util.Optional;

class InvokerConfigurationExtractor {
    public InvokerConfiguration extract(final Step step) {
        final ProcessingMethod processingMethod = ProcessMethodFinder.of(step).find();
        final Optional<ArtifactDefinition> producedArtifactDefinition = ProducedArtifactFinder.of(processingMethod).find();
        final var builder = InvokerConfiguration.builder()
                .step(step)
                .stepName(StepNameExtractor.of(step).extract())
                .processingMethod(processingMethod)
                .consumedArtifact(ConsumedArtifactsFinder.of(processingMethod).find());
        producedArtifactDefinition.ifPresent(builder::producedArtifact);
        return builder.build();
    }
}
