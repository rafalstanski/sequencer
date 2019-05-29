package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.configure.annotations.Artifact;
import com.hurricane.components.sequencer.configure.annotations.Name;
import com.hurricane.components.sequencer.configure.annotations.Process;
import com.hurricane.components.sequencer.runtime.Step;

@Name("consume1Produce2")
public class Consume1Produce2 implements Step {
    public static final String CHANGE_SUFFIX = "changed_";

    @Process
    @Artifact("artifact2")
    public String takeAndProduce(@Artifact("artifact1") final String artifact1) {
        return  CHANGE_SUFFIX + artifact1;
    }
}
