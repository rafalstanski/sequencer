package com.hurricane.application.process;

import com.hurricane.application.process.model.Person;
import com.hurricane.components.sequencer.annotations.Artifact;
import com.hurricane.components.sequencer.annotations.Process;
import com.hurricane.components.sequencer.sequence.Sequence;
import org.apache.commons.lang3.RandomUtils;

public class Generate implements Sequence {
    @Process
    @Artifact("referenceId")
    public Integer generate(@Artifact("person") final Person person) {
        System.out.println("generate: " + person);
        return RandomUtils.nextInt();
    }
}
