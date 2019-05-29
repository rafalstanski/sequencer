package com.hurricane.components.sequencer.configure.invoker;

import com.hurricane.components.sequencer.runtime.Step;
import com.hurricane.components.sequencer.runtime.invoker.PersistenceInvoker;
import com.hurricane.components.sequencer.runtime.invoker.StepInvoker;
import com.hurricane.components.sequencer.runtime.invoker.VoidInvoker;

public class StepInvokerBuilder {
    private final InvokerConfigurationExtractor extractor = new InvokerConfigurationExtractor();

    public StepInvoker build(final Step step) {
        final InvokerConfiguration configuration = extractor.extract(step);
        if (configuration.producesArtifact()) {
            return invokerHandingReturnValue(configuration);
        } else {
            return invokerForNoReturnValue(configuration);
        }
    }

    private PersistenceInvoker invokerHandingReturnValue(final InvokerConfiguration configuration) {
        return PersistenceInvoker.builder()
                .name(configuration.getStepName())
                .processingMethod(configuration.getProcessingMethod())
                .consumedArtifactsDefinitions(configuration.getConsumedArtifact())
                .producedArtifactDefinition(configuration.getProducedArtifact())
                .build();
    }

    private VoidInvoker invokerForNoReturnValue(final InvokerConfiguration configuration) {
        return VoidInvoker.builder()
                .name(configuration.getStepName())
                .processingMethod(configuration.getProcessingMethod())
                .consumedArtifactsDefinitions(configuration.getConsumedArtifact())
                .build();
    }
}

