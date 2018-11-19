package com.hurricane.components.sequencer.definition

import com.hurricane.components.sequencer.ExceptionHandler
import com.hurricane.components.sequencer.ExceptionHandlers
import com.hurricane.components.sequencer.Initial
import com.hurricane.components.sequencer.Reaction
import com.hurricane.components.sequencer.step.InstanceStepFactory
import com.hurricane.components.sequencer.step.Step
import com.hurricane.components.sequencer.step.StepFactory
import spock.lang.Specification

class SequencerDefinitionBuilderSpec extends Specification {
    def "should throw exception when trying to build from newly created builder"() {
        setup:
            def builder = new SequencerDefinitionBuilder()
        when:
            builder.build()
        then:
            thrown NullPointerException
    }

    def "should create definition when just Initial is set"() {
        setup:
            def builder = new SequencerDefinitionBuilder()
            builder.initial(Initial.non())
        when:
            def definition = builder.build()
        then:
            definition != null
    }

    def "should create default definition when just Initial is set"() {
        setup:
            def builder = new SequencerDefinitionBuilder()
            builder.initial(Initial.non())
        when:
            def definition = builder.build()
        then:
            definition.exceptionHandler == ExceptionHandlers.rethrow()
            definition.stepFactory instanceof InstanceStepFactory
            definition.stepsDefinitions.isEmpty()
    }

    def "should set custom exceptionHandler when provided"() {
        setup:
            def builder = new SequencerDefinitionBuilder()
            builder.initial(Initial.non())
            def handler = { RuntimeException exception -> return Reaction.IGNORE } as ExceptionHandler
            builder.exceptionHandler(handler)
        when:
            def definition = builder.build()
        then:
            definition.exceptionHandler == handler
    }

    def "should set custom stepFactory when provided"() {
        setup:
            def builder = new SequencerDefinitionBuilder()
            builder.initial(Initial.non())
            def factory = { stepDefinition -> return new Step() {} } as StepFactory
            builder.stepFactory(factory)
        when:
            def definition = builder.build()
        then:
            definition.stepFactory == factory
    }

    def "should add steps definition when provided"() {
        setup:
            def builder = new SequencerDefinitionBuilder()
            builder.initial(Initial.non())
            def step1 = (new Step() {}).getClass() as Class<? extends Step>
            def step2 = (new Step() {}).getClass() as Class<? extends Step>
            builder.step(step1)
            builder.step(step2)
        when:
            def definition = builder.build()
        then:
            definition.stepsDefinitions.size() == 2
            definition.stepsDefinitions == [step1, step2]
    }


    def "should fail when trying to set NULL step"() {
        setup:
            def builder = new SequencerDefinitionBuilder()
            builder.initial(Initial.non())
        when:
            builder.step(null)
        then:
            thrown NullPointerException
    }

    def "should fail when trying to set NULL step factory"() {
        setup:
            def builder = new SequencerDefinitionBuilder()
            builder.initial(Initial.non())
        when:
            builder.stepFactory(null)
        then:
            thrown NullPointerException
    }


    def "should fail when trying to set NULL exception handler"() {
        setup:
            def builder = new SequencerDefinitionBuilder()
            builder.initial(Initial.non())
        when:
            builder.exceptionHandler(null)
        then:
            thrown NullPointerException
    }
}
