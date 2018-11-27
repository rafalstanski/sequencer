package com.hurricane.components.sequencer;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Builder
@Data
public class SequencerResult {
    private final Collection<Artifact> artifacts;
    private final List<String> steps;
    private final List<String> executedSteps;
    private final List<StepInvokeError> errors;

    public boolean success() {
        return errors.isEmpty();
    }
}
