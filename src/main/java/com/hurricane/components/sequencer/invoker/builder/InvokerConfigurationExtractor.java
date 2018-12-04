package com.hurricane.components.sequencer.invoker.builder;

import com.hurricane.components.sequencer.ArtifactDefinition;
import com.hurricane.components.sequencer.invoker.ProcessingMethod;
import com.hurricane.components.sequencer.step.Step;
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
