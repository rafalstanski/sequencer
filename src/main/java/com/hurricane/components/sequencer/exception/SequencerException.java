package com.hurricane.components.sequencer.exception;

public class SequencerException extends RuntimeException {
    public SequencerException(String message) {
        super(message);
    }

    public SequencerException(String message, Throwable cause) {
        super(message, cause);
    }
}
