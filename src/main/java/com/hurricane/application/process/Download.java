package com.hurricane.application.process;

import com.hurricane.components.sequencer.configure.annotations.Artifact;
import com.hurricane.components.sequencer.configure.annotations.Name;
import com.hurricane.components.sequencer.configure.annotations.Process;
import com.hurricane.components.sequencer.runtime.Step;

import static com.hurricane.application.process.SampleProcessArtifacts.REFERENCE_ID;

@Name("download")
public class Download implements Step {
    @Process
    public void download(@Artifact(REFERENCE_ID) final Integer referenceId) {
        System.out.println("download: " + referenceId);
    }
}
