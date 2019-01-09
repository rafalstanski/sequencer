package com.hurricane.components.sequencer.runtime;

import com.hurricane.components.sequencer.runtime.invoker.StepInvoker;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SequencerContract {
    private final List<StepInvoker> invokers;
    private final ExceptionHandler exceptionHandler;
    private final ContextInitalPopulator populator;
}
