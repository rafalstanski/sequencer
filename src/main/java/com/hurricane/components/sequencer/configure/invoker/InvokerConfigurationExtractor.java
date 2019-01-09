package com.hurricane.components.sequencer.configure.invoker;

import com.hurricane.components.sequencer.runtime.ArtifactDefinition;
import com.hurricane.components.sequencer.runtime.Step;
import com.hurricane.components.sequencer.runtime.invoker.ProcessingMethod;
import lombok.var;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.Method;
import java.util.List;

class InvokerConfigurationExtractor {
    private final ProcessMethodFinder processMethodFinder = new ProcessMethodFinder();
    private final ConsumedArtifactsFinder consumedArtifactsFinder = new ConsumedArtifactsFinder();
    private final ProducedArtifactFinder producedArtifactFinder = new ProducedArtifactFinder();
    private final StepNameExtractor stepNameExtractor = new StepNameExtractor();

    public InvokerConfiguration extract(final Step step) {
        Validate.notNull(step, "Unable to extract invoker's configuration from null step");
        final Method processMethod = processMethodFinder.find(step);
        final var builder = InvokerConfiguration.builder()
                .step(step)
                .stepName(name(step))
                .processingMethod(ProcessingMethod.of(processMethod, step))
                .consumedArtifact(consumedArtifacts(processMethod));
        configureProducedArtifact(processMethod, builder);
        return builder.build();
    }

    private String name(final Step step) {
        return stepNameExtractor.extract(step);
    }

    private List<ArtifactDefinition> consumedArtifacts(final Method processMethod) {
        return consumedArtifactsFinder.find(processMethod);
    }

    private void configureProducedArtifact(final Method processMethod, final InvokerConfiguration.InvokerConfigurationBuilder builder) {
        final var producedArtifactDefinition = producedArtifactFinder.find(processMethod);
        producedArtifactDefinition.ifPresent(builder::producedArtifact);
    }
}
