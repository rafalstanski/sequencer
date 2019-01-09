package com.hurricane.components.sequencer.configure.invoker;

import com.hurricane.components.sequencer.configure.annotations.Name;
import com.hurricane.components.sequencer.runtime.NamedStep;
import com.hurricane.components.sequencer.runtime.Step;
import org.apache.commons.lang3.Validate;

class StepNameExtractor {
    public String extract(final Step step) {
        Validate.notNull(step, "Unable to extract step's name from a null step");
        if (byInheritance(step)) {
            return fromInheritance(step);
        } else if (byAnnotation(step)) {
            return fromAnnotation(step);
        } else {
            return fromDefault(step);
        }
    }

    private boolean byInheritance(final Step step) {
        return step instanceof NamedStep;
    }

    private String fromInheritance(final Step step) {
        return ((NamedStep) step).name();
    }

    private boolean byAnnotation(final Step step) {
        return step.getClass().isAnnotationPresent(Name.class);
    }

    private String fromAnnotation(final Step step) {
        return step.getClass().getAnnotation(Name.class).value();
    }

    private String fromDefault(final Step step) {
        return step.getClass().getCanonicalName();
    }
}
