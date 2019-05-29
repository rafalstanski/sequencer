package com.hurricane.components.sequencer.runtime.invoker;

import com.hurricane.components.sequencer.exception.StepInvokeException;
import lombok.RequiredArgsConstructor;

public interface InvokeResult {
    static InvokeResult success(final Object returnValue) {
        return new Success(returnValue);
    }

    static InvokeResult failed(final Throwable throwable) {
        return new Failure(throwable);
    }

    boolean isSuccess();
    boolean isFailure();
    Object getResult();
    Throwable getCause();

    @RequiredArgsConstructor
    final class Success implements InvokeResult {
        private final Object result;

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public boolean isFailure() {
            return false;
        }

        @Override
        public Object getResult() {
            return result;
        }

        @Override
        public Throwable getCause() {
            throw new UnsupportedOperationException("Success result type does not support exceptions");
        }
    }

    @RequiredArgsConstructor
    final class Failure implements InvokeResult {
        private final Throwable cause;

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public boolean isFailure() {
            return true;
        }

        @Override
        public Object getResult() {
            throw StepInvokeException.exceptionWhileExecution(cause);
        }

        @Override
        public Throwable getCause() {
            return cause;
        }
    }
}



