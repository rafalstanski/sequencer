package com.hurricane.application.process;

import com.hurricane.application.process.model.Person;
import com.hurricane.components.sequencer.annotations.Artifact;
import com.hurricane.components.sequencer.annotations.Process;
import com.hurricane.components.sequencer.step.Step;
import org.apache.commons.lang3.RandomUtils;

import static com.hurricane.application.process.SampleProcessArtifacts.PERSON;
import static com.hurricane.application.process.SampleProcessArtifacts.REFERENCE_ID;

public class Generate implements Step {
    @Process
    @Artifact(REFERENCE_ID)
    public Integer generate(@Artifact(PERSON) final Person person) {
        System.out.println("generate: " + person);
        return RandomUtils.nextInt();
    }
}
