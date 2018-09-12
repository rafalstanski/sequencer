package com.hurricane.components.sequencer;

import lombok.Builder;
import lombok.Data;

@Data(staticConstructor = "of")
public class SequenceInvokeError {
    private final String sequenceName;
    private final RuntimeException exception;
}
