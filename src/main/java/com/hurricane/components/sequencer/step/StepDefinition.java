package com.hurricane.components.sequencer.step;

import lombok.Data;
import org.apache.commons.lang3.Validate;

import java.util.Objects;

@Data
public class StepDefinition {
    private final Step instance;
    private final Class<? extends Step> instanceClass;

    private StepDefinition(Step instance, Class<? extends Step> instanceClass) {
        this.instance = instance;
        this.instanceClass = instanceClass;
    }

    public static StepDefinition fromInstance(final Step instance) {
        Validate.notNull(instance, "Instance parameter cannot be null when creating StepDefinition for Instance");
        return new StepDefinition(instance, null);
    }

    public static StepDefinition fromClass(final Class<? extends Step> instanceClass) {
        Validate.notNull(instanceClass, "Instance's class parameter cannot be null when creating StepDefinition for Class");
        return new StepDefinition(null, instanceClass);
    }

    public boolean isInstanceProvided() {
        return Objects.nonNull(instance);
    }
}
