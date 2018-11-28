package com.hurricane.components.sequencer.invoker;

import com.hurricane.components.sequencer.ArtifactDefinition;

import java.util.List;
import java.util.Optional;

public interface StepInvoker {
    String getName();
    InvokeResult invoke(InvokerContext context);
    List<ArtifactDefinition> consumedArtifacts();
    Optional<ArtifactDefinition> producedArtifact();
}
