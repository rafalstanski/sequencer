package com.hurricane.components.sequencer.exception;

import com.hurricane.components.sequencer.annotations.Process;
import com.hurricane.components.sequencer.sequence.Sequence;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.stream.Collectors;

public class ProcessMethodMissingException extends SequenceException {
    private ProcessMethodMissingException(String message) {
        super(message);
    }

    public static ProcessMethodMissingException notFound(final Sequence sequence) {
        return new ProcessMethodMissingException(createNotFoundMessage(sequence));
    }

    public static ProcessMethodMissingException tooMany(final Sequence sequence, final Collection<Method> processingMethods) {
        return new ProcessMethodMissingException(createTooManyMessage(sequence, processingMethods));
    }

    private static String createNotFoundMessage(final Sequence sequence) {
        return "Unable to find process method within Sequence class /" +
                sequence.getClass().getCanonicalName() +
                "/. Remember that method need to be annotated with @" +
                Process.class.getName();
    }

    private static String createTooManyMessage(final Sequence sequence, final Collection<Method> processingMethods) {
        final String methodNames = processingMethods.stream()
                .map(Method::getName)
                .collect(Collectors.joining(", "));
        return "There is to many methods within Sequence class /" +
                sequence.getClass().getCanonicalName() +
                "/annotated with @" +
                Process.class.getName() +
                "There need to be only one. Found methods: " +
                methodNames;
    }
}
