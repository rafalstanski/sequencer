package com.hurricane.components.sequencer.configure.invoker;

import com.hurricane.components.sequencer.configure.annotations.Process;
import com.hurricane.components.sequencer.exception.ProcessMethodMissingException;
import com.hurricane.components.sequencer.runtime.Step;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class ProcessMethodFinder {
    public Method find(final Step step) {
        final List<Method> processMethods = findProcessMethods(step);
        if (foundExactlyOneMethod(processMethods)) {
            return firstMethod(processMethods);
        } else {
            throw handleIncorrectState(step, processMethods);
        }
    }

    private List<Method> findProcessMethods(final Step step) {
        final Method[] methods = step.getClass().getMethods();
        return Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(Process.class))
                .collect(Collectors.toList());
    }

    private Method firstMethod(final List<Method> processMethods) {
        return processMethods.get(0);
    }

    private RuntimeException handleIncorrectState(final Step step, final List<Method> processMethods) {
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
