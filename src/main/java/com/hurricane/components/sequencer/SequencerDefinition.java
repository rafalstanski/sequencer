package com.hurricane.components.sequencer;

import com.hurricane.components.sequencer.sequence.InstanceSequenceFactory;
import com.hurricane.components.sequencer.sequence.Sequence;
import com.hurricane.components.sequencer.sequence.SequenceFactory;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data(staticConstructor = "of")
public class SequencerDefinition<T> {
    private final Initial<T> initial;
    private List<Class<? extends Sequence>> sequencesClasses = Collections.emptyList();
    private SequenceFactory sequenceFactory = new InstanceSequenceFactory();
    private ExceptionHandler exceptionHandler = ExceptionHandlers.rethrow();
}