package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.configure.annotations.Artifact;
import com.hurricane.components.sequencer.configure.annotations.Name;
import com.hurricane.components.sequencer.configure.annotations.Process;
import com.hurricane.components.sequencer.runtime.Step;

@Name("consume1")
public class Transform1 implements Step {
    public static final String TRANSFORM_POSTFIX = "_transformed";

    @Process
    @Artifact("artifact1")
    public String takeAndTransform(@Artifact("artifact1") final String artifact1) {
        return artifact1 + TRANSFORM_POSTFIX;
    }
}
