package com.hurricane.application.process;

import com.hurricane.components.sequencer.annotations.Artifact;
import com.hurricane.components.sequencer.annotations.Process;
import com.hurricane.components.sequencer.sequence.Sequence;

public class Zipper implements Sequence {
    @Process
    public void zip(@Artifact("referenceId") final Integer referenceId) {
        System.out.println("zip: " + referenceId);
    }
}
