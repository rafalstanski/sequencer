package com.hurricane.components.sequencer.runtime.invoker;

import com.hurricane.components.sequencer.exception.StepInvokeException;
import com.hurricane.components.sequencer.runtime.Artifact;
import com.hurricane.components.sequencer.runtime.Step;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Data(staticConstructor = "of")
public class ProcessingMethod {
    private final Method processMethod;
    private final Step target;

    public InvokeResult invoke(final List<Artifact> artifacts) {
        final Object[] values = extractValues(artifacts);
        try {
            final Object returnValue = processMethod.invoke(target, values);
            return handleSuccess(returnValue);
        } catch (final IllegalAccessException e) {
            return handleFailure(StepInvokeException.illegalAccess(processMethod, target, e));
        } catch (final InvocationTargetException e) {
            return handleFailure(e.getTargetException());
        } catch (final Throwable t) {
            return handleFailure(t);
        }
    }

    private Object[] extractValues(final List<Artifact> artifacts) {
        return artifacts.stream()
                .map(Artifact::getValue)
                .toArray();
    }

    private InvokeResult handleSuccess(final Object returnValue) {
        return new Success(returnValue);
    }

    private InvokeResult handleFailure(final Throwable throwable) {
        if (nonRecoverable(throwable)) {
            throw (Error)throwable;
        } else {
            return new Failure(throwable);
        }
    }

    private boolean nonRecoverable(final Throwable cause) {
        // inspired by vavr library's Try class. Those exceptions are considered to be fatal/non-recoverable
        return cause instanceof LinkageError || cause instanceof ThreadDeath || cause instanceof VirtualMachineError;
    }
}
