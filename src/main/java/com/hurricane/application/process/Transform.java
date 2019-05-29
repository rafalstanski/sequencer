package com.hurricane.application.process;

import com.hurricane.application.process.model.Person;
import com.hurricane.components.sequencer.configure.annotations.Artifact;
import com.hurricane.components.sequencer.configure.annotations.Process;
import com.hurricane.components.sequencer.runtime.Step;
import org.apache.commons.lang3.StringUtils;

import static com.hurricane.application.process.SampleProcessArtifacts.NAME;
import static com.hurricane.application.process.SampleProcessArtifacts.PERSON;

public class Transform implements Step {
    @Process
    @Artifact(PERSON)
    public Person transform(@Artifact(NAME) final String name) {
        System.out.println("transforming: " + name);
        final String[] parts = StringUtils.split(name, ' ');

        return new Person(parts[0], parts[1]);
    }
}
