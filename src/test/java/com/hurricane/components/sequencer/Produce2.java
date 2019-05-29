package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.configure.annotations.Artifact;
import com.hurricane.components.sequencer.configure.annotations.Name;
import com.hurricane.components.sequencer.configure.annotations.Process;
import com.hurricane.components.sequencer.runtime.Step;

@Name("produce2")
public class Produce2 implements Step {

    @Process
    @Artifact("artifact2")
    public String create() {
        return "artifact2_value";
    }
}
