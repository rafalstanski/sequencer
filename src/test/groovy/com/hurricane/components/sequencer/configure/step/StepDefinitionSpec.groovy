package com.hurricane.components.sequencer.configure.step

import com.hurricane.components.sequencer.runtime.Step
import spock.lang.Specification

class StepDefinitionSpec extends Specification {
    static final STEP_INSTANCE = new Step(){}
    static final STEP_CLASS = STEP_INSTANCE.getClass() as Class<? extends Step>

    def "should create definition using instance"() {
        when:
            def definition = StepDefinition.fromInstance(STEP_INSTANCE)
        then:
            definition.isInstanceProvided()
            definition.instance != null
            definition.instanceClass == null
    }

    def "should create definition using class"() {
        when:
            def definition = StepDefinition.fromClass(STEP_CLASS)
        then:
            !definition.isInstanceProvided()
            definition.instance == null
            definition.instanceClass != null
    }

    def "should fail when trying to set create definition base on NULL instance"() {
        when:
            StepDefinition.fromInstance(null)
        then:
            thrown NullPointerException
    }

    def "should fail when trying to set create definition base on NULL class"() {
        when:
           StepDefinition.fromClass(null)
        then:
            thrown NullPointerException
    }

}
