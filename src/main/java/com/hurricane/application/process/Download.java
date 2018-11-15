package com.hurricane.application.process;

import com.hurricane.components.sequencer.annotations.Artifact;
import com.hurricane.components.sequencer.annotations.Process;
import com.hurricane.components.sequencer.step.Step;

import static com.hurricane.application.process.SampleProcessArtifacts.REFERENCE_ID;

public class Download implements Step {
    @Process
    public void download(@Artifact(REFERENCE_ID) final Integer referenceId) {
        System.out.println("download: " + referenceId);
    }
}
