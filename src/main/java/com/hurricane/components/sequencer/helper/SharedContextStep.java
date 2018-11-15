package com.hurricane.components.sequencer.helper;

import com.hurricane.components.sequencer.annotations.Artifact;
import com.hurricane.components.sequencer.step.Step;
import com.hurricane.components.sequencer.annotations.Process;

public interface SharedContextStep<CONTEXT> extends Step {
    @Process
    void process(@Artifact("context") CONTEXT context);
}
