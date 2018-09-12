package com.hurricane.application;

import com.hurricane.application.process.Download;
import com.hurricane.application.process.Generate;
import com.hurricane.application.process.Transform;
import com.hurricane.application.process.Zipper;
import com.hurricane.components.sequencer.Sequencer;
import com.hurricane.components.sequencer.SequencerBuilder;
import com.hurricane.components.sequencer.SequencerResult;

import static com.hurricane.components.sequencer.Initializer.string;

public class Main {
    public static void main(String[] args) {
        final Sequencer<String> sequencer = SequencerBuilder
                .initializedBy(string("name"))
                .start(Transform.class)
                .next(Generate.class)
                .next(Download.class)
                .next(Zipper.class)
                .build();

        final SequencerResult result = sequencer.start("Rafal Stanski");
    }
}