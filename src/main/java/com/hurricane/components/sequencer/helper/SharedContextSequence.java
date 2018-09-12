package com.hurricane.components.sequencer.helper;

import com.hurricane.components.sequencer.annotations.Artifact;
import com.hurricane.components.sequencer.sequence.Sequence;
import com.hurricane.components.sequencer.annotations.Process;

public interface SharedContextSequence<CONTEXT> extends Sequence {
    @Process
    void process(@Artifact("context") CONTEXT context);
}
