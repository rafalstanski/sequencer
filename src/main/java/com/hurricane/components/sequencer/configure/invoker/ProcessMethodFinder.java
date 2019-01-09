package com.hurricane.components.sequencer.configure.invoker;

import com.hurricane.components.sequencer.configure.annotations.Process;
import com.hurricane.components.sequencer.exception.ProcessMethodMissingException;
import com.hurricane.components.sequencer.runtime.Step;
import com.hurricane.components.sequencer.runtime.invoker.ProcessingMethod;
import lombok.AllArgsConstructor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(staticName = "of")
class ProcessMethodFinder {
    final Step step;

    public ProcessingMethod find() {
        final List<Method> processMethods = findProcessMethods(step);
        if (foundExactlyOneMethod(processMethods)) {
            return createProcessingMethod(processMethods.get(0));
        } else {
            throw handleIncorrectState(processMethods);
        }
    }

    private List<Method> findProcessMethods(final Step step) {
        final Method[] methods = step.getClass().getMethods();
        return Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(Process.class))
                .collect(Collectors.toList());
    }

    private ProcessingMethod createProcessingMethod(final Method processMethod) {
        return ProcessingMethod.of(processMethod, step);
    }

    private RuntimeException handleIncorrectState(final List<Method> processMethods) {
        if (notFoundProcessingMethod(processMethods)) {
            return ProcessMethodMissingException.notFound(step);
        } else {
            return ProcessMethodMissingException.tooMany(step, processMethods);
        }
    }

    private boolean foundExactlyOneMethod(final List<Method> processMethods) {
        return processMethods.size() == 1;
    }

    private boolean notFoundProcessingMethod(final List<Method> processMethods) {
        return processMethods.size() == 0;
    }
}
