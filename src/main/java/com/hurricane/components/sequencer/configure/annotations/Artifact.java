package com.hurricane.components.sequencer.configure.annotations;


import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, PARAMETER, FIELD})
@Retention(RUNTIME)
@Inherited
@Documented
public @interface Artifact {
    String value();
}
