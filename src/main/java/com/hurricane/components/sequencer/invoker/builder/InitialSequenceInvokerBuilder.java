package com.hurricane.components.sequencer.invoker.builder;

import com.hurricane.components.sequencer.Initial;
import com.hurricane.components.sequencer.invoker.InitialSequenceInvoker;

import java.util.Optional;

public class InitialSequenceInvokerBuilder {
    public Optional<InitialSequenceInvoker> build(final Initial<?> initial) {
        if (initial.expectInput()) {
            final ProducerDefinition producerDefinition = ProducerDefinition.builder()
                    .name(initial.getArtifactName())
                    .build();
            return Optional.of(InitialSequenceInvoker.of(producerDefinition));
        } else {
            return Optional.empty();
        }
    }
}
