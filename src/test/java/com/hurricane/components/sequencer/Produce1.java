package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.configure.annotations.Artifact;
import com.hurricane.components.sequencer.configure.annotations.Name;
import com.hurricane.components.sequencer.configure.annotations.Process;
import com.hurricane.components.sequencer.runtime.Step;

@Name("produce1")
public class Produce1 implements Step {

    @Process
    @Artifact("artifact1")
    public String create() {
        return "artifact1_value";
    }
}
