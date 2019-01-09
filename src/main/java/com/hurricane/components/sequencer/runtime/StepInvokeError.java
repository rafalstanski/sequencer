package com.hurricane.components.sequencer.runtime;

import lombok.Data;

@Data(staticConstructor = "of")
public class StepInvokeError {
    private final String stepName;
    private final Throwable cause;
}
