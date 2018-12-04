package com.hurricane.application.process;

import com.hurricane.components.sequencer.Sequencer;
import com.hurricane.components.sequencer.SequencerBuilder;
import com.hurricane.components.sequencer.SequencerResult;

import static com.hurricane.application.process.SampleProcessArtifacts.NAME;
import static com.hurricane.components.sequencer.Initializer.string;

public class Main {
    public static void main(String[] args) {
        final Sequencer<String> sequencer = SequencerBuilder
                .initializedBy(string(NAME))
                .start(Transform.class)
                .next(Generate.class)
                .next(Download.class)
                .next(Zipper.class)
                .build();

        final SequencerResult result = sequencer.start("Rafal Stanski");
        System.out.println("Successful run: " + result.success());
        System.out.println("Step executed: " + result.getExecutedSteps());
        System.out.println("Artifacts: " + result.getArtifacts());
        System.out.println("Errors: " + result.getErrors());
    }
}