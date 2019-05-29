package com.hurricane.components.sequencer;


import com.hurricane.components.sequencer.configure.annotations.Artifact;
import com.hurricane.components.sequencer.configure.annotations.Name;
import com.hurricane.components.sequencer.configure.annotations.Process;
import com.hurricane.components.sequencer.runtime.Step;

@Name("consume1")
public class Consume1 implements Step {
    @Process
    public void take(@Artifact("artifact1") final String consumedValue) {
    }
}
