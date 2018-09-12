package com.hurricane.components.sequencer.exception;

public class SequenceException extends RuntimeException {
    public SequenceException(String message) {
        super(message);
    }

    public SequenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
