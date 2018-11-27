package com.hurricane.components.sequencer.invoker;

import com.hurricane.components.sequencer.Artifact;
import com.hurricane.components.sequencer.exception.StepInvokeException;
import com.hurricane.components.sequencer.step.Step;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Data(staticConstructor = "of")
public class ProcessingMethod {
    private final Method processMethod;
    private final Step target;

    public Object invoke(final List<Artifact> artifacts) {
        final Object[] values = extractValues(artifacts);
        try {
            return processMethod.invoke(target, values);
        } catch (final IllegalAccessException e) {
            throw StepInvokeException.illegalAccess(processMethod, target, e);
        } catch (final InvocationTargetException e) {
            throw StepInvokeException.executionError(processMethod, target, e.getTargetException());
        }
    }

    private Object[] extractValues(final List<Artifact> artifacts) {
        return artifacts.stream()
                .map(Artifact::getValue)
                .toArray();
    }
}
