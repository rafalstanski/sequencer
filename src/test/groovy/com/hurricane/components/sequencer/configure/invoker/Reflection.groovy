package com.hurricane.components.sequencer.configure.invoker

import java.lang.reflect.Method
import java.lang.reflect.Parameter

final class Reflection {
    static Parameter parameter(final Object target, final String methodName, final int paramIndex = 0) {
        def method = method(target, methodName)
        method.parameters[paramIndex]
    }

    static Method method(final Object target, final String methodName) {
        target.getClass().getMethods().find { it.name == methodName }
    }
}
