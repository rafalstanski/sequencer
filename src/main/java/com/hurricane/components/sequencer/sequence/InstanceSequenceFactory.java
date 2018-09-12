package com.hurricane.components.sequencer.sequence;

public class InstanceSequenceFactory implements SequenceFactory {
    @Override
    public Sequence create(Class<? extends Sequence> sequenceClass) {
        try {
            return sequenceClass.newInstance();
        } catch (InstantiationException e) {
            //TODO throw appropriate exception
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            //TODO throw appropriate exceptions
            throw new RuntimeException(e);
        }
    }
}
