package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.configure.annotations.Artifact;
import com.hurricane.components.sequencer.configure.annotations.Name;
import com.hurricane.components.sequencer.configure.annotations.Process;
import com.hurricane.components.sequencer.runtime.Step;

@Name("Combine1_2_Return3")
public class Combine1_2_Return3 implements Step {
    @Process
    @Artifact("artifact3")
    public String takeAndCombine(
            @Artifact("artifact1") final String artifact1,
            @Artifact("artifact2") final String artifact2) {
        return  artifact1 + "_" + artifact2;
    }
}
