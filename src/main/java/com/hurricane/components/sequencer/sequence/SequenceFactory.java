package com.hurricane.components.sequencer.sequence;

public interface SequenceFactory {
    Sequence create(Class<? extends Sequence> sequenceClass);
}
