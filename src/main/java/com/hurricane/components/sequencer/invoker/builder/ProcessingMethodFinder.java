package com.hurricane.components.sequencer.invoker.builder;

import com.hurricane.components.sequencer.annotations.Process;
import com.hurricane.components.sequencer.exception.ProcessMethodMissingException;
import com.hurricane.components.sequencer.sequence.Sequence;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(staticName = "of")
class ProcessingMethodFinder {
    final Sequence sequence;

    public Method find() {
        Validate.notNull(sequence, "Sequence need to be set before searching for processing method");

        final Method[] methods = sequence.getClass().getMethods();
        final List<Method> processMethods = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(Process.class))
                .collect(Collectors.toList());
        if (processMethods.size() == 1) {
            return processMethods.get(0);
        } else if (processMethods.size() == 1) {
            throw ProcessMethodMissingException.notFound(sequence);
        } else {
            throw ProcessMethodMissingException.tooMany(sequence, processMethods);
        }
    }
}
