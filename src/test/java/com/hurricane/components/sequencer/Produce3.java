package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.configure.annotations.Artifact;
import com.hurricane.components.sequencer.configure.annotations.Name;
import com.hurricane.components.sequencer.configure.annotations.Process;
import com.hurricane.components.sequencer.runtime.Step;

@Name("produce3")
public class Produce3 implements Step {

    @Process
    @Artifact("artifact3")
    public String create() {
        return "artifact3_value";
    }
}
