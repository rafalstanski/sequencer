package com.hurricane.components.sequencer.invoker.builder;

import com.hurricane.components.sequencer.Initial;
import com.hurricane.components.sequencer.invoker.InitialStepInvoker;

import java.util.Optional;

public class InitialStepInvokerBuilder {
    public Optional<InitialStepInvoker> build(final Initial<?> initial) {
        if (initial.expectInput()) {
            final ProducerDefinition producerDefinition = ProducerDefinition.builder()
                    .name(initial.getArtifactName())
                    .build();
            return Optional.of(InitialStepInvoker.of(producerDefinition));
        } else {
            return Optional.empty();
        }
    }
}
