package com.hurricane.components.sequencer.definition

import com.hurricane.components.sequencer.ExceptionHandler
import com.hurricane.components.sequencer.ExceptionHandlers
import com.hurricane.components.sequencer.Initial
import com.hurricane.components.sequencer.Reaction
import com.hurricane.components.sequencer.step.InstanceStepFactory
import com.hurricane.components.sequencer.step.Step
import com.hurricane.components.sequencer.step.StepDefinition
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
            def builder = sequencerDefinitionBuilderWithInitialNon()
            def handler = { RuntimeException exception -> return Reaction.IGNORE } as ExceptionHandler
            builder.exceptionHandler(handler)
        when:
            def definition = builder.build()
        then:
            definition.exceptionHandler == handler
    }

    def "should set custom stepFactory when provided"() {
        setup:
            def builder = sequencerDefinitionBuilderWithInitialNon()
            def factory = { stepDefinition -> return new Step() {} } as StepFactory
            builder.stepFactory(factory)
        when:
            def definition = builder.build()
        then:
            definition.stepFactory == factory
    }

    def "should add steps definition when step classes provided"() {
        setup:
            def builder = sequencerDefinitionBuilderWithInitialNon()
            def stepClass1 = (new Step() {}).getClass() as Class<? extends Step>
            def stepClass2 = (new Step() {}).getClass() as Class<? extends Step>
            builder.step(stepClass1)
            builder.step(stepClass2)
        when:
            def definition = builder.build()
        then:
            definition.stepsDefinitions.size() == 2
            definition.stepsDefinitions == [StepDefinition.fromClass(stepClass1), StepDefinition.fromClass(stepClass2)]
    }

    def "should add steps definition when step instances provided"() {
        setup:
        def builder = sequencerDefinitionBuilderWithInitialNon()
        def stepInstance1 = new Step() {}
        def stepInstance2 = new Step() {}
        builder.step(stepInstance1)
        builder.step(stepInstance2)
        when:
        def definition = builder.build()
        then:
        definition.stepsDefinitions.size() == 2
        definition.stepsDefinitions == [StepDefinition.fromInstance(stepInstance1), StepDefinition.fromInstance(stepInstance2)]
    }

    def "should fail when trying to set NULL step class"() {
        setup:
            def builder = sequencerDefinitionBuilderWithInitialNon()
            def nullStep = null as Step
        when:
            builder.step(nullStep)
        then:
            thrown NullPointerException
    }

    def "should fail when trying to set NULL step instance"() {
        setup:
            def builder = sequencerDefinitionBuilderWithInitialNon()
            def nullStep = null as Class<? extends Step>
        when:
            builder.step(nullStep)
        then:
            thrown NullPointerException
    }

    def "should fail when trying to set NULL step factory"() {
        setup:
            def builder = sequencerDefinitionBuilderWithInitialNon()
        when:
            builder.stepFactory(null)
        then:
            thrown NullPointerException
    }


    def "should fail when trying to set NULL exception handler"() {
        setup:
            def builder = sequencerDefinitionBuilderWithInitialNon()
        when:
            builder.exceptionHandler(null)
        then:
            thrown NullPointerException
    }

    private def sequencerDefinitionBuilderWithInitialNon() {
        final def builder = new SequencerDefinitionBuilder()
        builder.initial(Initial.non())
        return builder
    }
}
