package com.hurricane.components.sequencer;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Builder
@Data
public class SequencerResult {
    private final Map<String, Object> artifacts;
    private final List<String> usedSequences;
    private final List<SequenceInvokeError> errors;

    public boolean success() {
        return errors.isEmpty();
    }
}
