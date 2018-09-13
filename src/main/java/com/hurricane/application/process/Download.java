package com.hurricane.application.process;

import com.hurricane.components.sequencer.annotations.Artifact;
import com.hurricane.components.sequencer.annotations.Process;
import com.hurricane.components.sequencer.sequence.Sequence;

import static com.hurricane.application.process.SampleProcessArtifacts.REFERENCE_ID;

public class Download implements Sequence {
    @Process
    public void download(@Artifact(REFERENCE_ID) final Integer referenceId) {
        System.out.println("download: " + referenceId);
    }
}
