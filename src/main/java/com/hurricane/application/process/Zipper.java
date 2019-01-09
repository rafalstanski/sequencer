package com.hurricane.application.process;

import com.hurricane.components.sequencer.configure.annotations.Artifact;
import com.hurricane.components.sequencer.configure.annotations.Process;
import com.hurricane.components.sequencer.runtime.Step;

import static com.hurricane.application.process.SampleProcessArtifacts.REFERENCE_ID;

public class Zipper implements Step {
    @Process
    public void zip(@Artifact(REFERENCE_ID) final Integer referenceId) {
        System.out.println("zip: " + referenceId);
        throw new RuntimeException("zipper exception");
    }
}
