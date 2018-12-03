package com.hurricane.components.sequencer.definition;

import com.hurricane.components.sequencer.ContextInitalPopulator;
import com.hurricane.components.sequencer.ExceptionHandler;
import com.hurricane.components.sequencer.invoker.StepInvoker;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SequencerDefinition {
    private final List<StepInvoker> invokers;
    private final ExceptionHandler exceptionHandler;
    private final ContextInitalPopulator populator;
}
