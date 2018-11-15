package com.hurricane.components.sequencer.invoker.builder;

import com.hurricane.components.sequencer.invoker.BaseStepInvoker;
import com.hurricane.components.sequencer.invoker.StepInvoker;
import com.hurricane.components.sequencer.step.Step;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

public class StepInvokerBuilder {
    public StepInvoker build(final Step step) {
        final Method processingMethod = ProcessingMethodFinder.of(step).find();
        final List<ConsumerDefinition> consumerDefinitions = ConsumeFinder.of(processingMethod).find();
        final Optional<ProducerDefinition> producerDefinition = ProduceFinder.of(processingMethod).find();
        return BaseStepInvoker.builder()
                .name(step.getClass().getCanonicalName())
                .step(step)
                .processingMethod(processingMethod)
                .consumerDefinitions(consumerDefinitions)
                .producerDefinition(producerDefinition)
                .build();
    }
}


