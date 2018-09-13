package com.hurricane.application.process;

import com.hurricane.application.process.model.Person;
import com.hurricane.components.sequencer.annotations.Artifact;
import com.hurricane.components.sequencer.annotations.Process;
import com.hurricane.components.sequencer.sequence.Sequence;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import static com.hurricane.application.process.SampleProcessArtifacts.NAME;
import static com.hurricane.application.process.SampleProcessArtifacts.PERSON;

public class Transform implements Sequence {
    @Process
    @Artifact(PERSON)
    public Person transform(@Artifact(NAME) final String name) {
        System.out.println("transforming: " + name);
        final String nameToSplit = Objects.toString(name, name);
        final String[] parts = StringUtils.split(nameToSplit, ' ');
        return new Person(parts[0], parts[1]);
    }
}
