package com.hurricane.components.sequencer.configure

import com.hurricane.components.sequencer.configure.step.InstanceStepFactory
import com.hurricane.components.sequencer.configure.step.StepDefinition
import com.hurricane.components.sequencer.configure.step.StepFactory
import com.hurricane.components.sequencer.runtime.ExceptionHandler
import com.hurricane.components.sequencer.runtime.Initial
import com.hurricane.components.sequencer.runtime.Reaction
import com.hurricane.components.sequencer.runtime.Step
import spock.lang.Specification

class SequencerBuildConfigurerSpec extends Specification {
    def "should throw exception when trying to build from newly created configurer"() {
        setup:
            def configurer = new SequencerBuildConfigurer()
        when:
            configurer.provide()
        then:
            thrown NullPointerException
    }

    def "should create configuration when just Initial is set"() {
        setup:
            def configurer = new SequencerBuildConfigurer()
            configurer.initial(Initial.non())
        when:
            def configuration = configurer.provide()
        then:
            configuration != null
    }

    def "should create default configuration when just Initial is set"() {
        setup:
            def configurer = new SequencerBuildConfigurer()
            configurer.initial(Initial.non())
        when:
            def configuration = configurer.provide()
        then:
            configuration.exceptionHandler == ExceptionHandlers.interrupt()
            configuration.stepFactory instanceof InstanceStepFactory
            configuration.stepsDefinitions.isEmpty()
    }

    def "should set custom exceptionHandler when provided"() {
        setup:
            def configurer = configurerWithInitialNon()
            def handler = { RuntimeException exception -> return Reaction.IGNORE } as ExceptionHandler
            configurer.exceptionHandler(handler)
        when:
            def configuration = configurer.provide()
        then:
            configuration.exceptionHandler == handler
    }

    def "should set custom stepFactory when provided"() {
        setup:
            def configurer = configurerWithInitialNon()
            def factory = { stepDefinition -> return new Step() {} } as StepFactory
            configurer.stepFactory(factory)
        when:
            def configuration = configurer.provide()
        then:
            configuration.stepFactory == factory
    }

    def "should add steps configuration when step classes provided"() {
        setup:
            def configurer = configurerWithInitialNon()
            def stepClass1 = (new Step() {}).getClass() as Class<? extends Step>
            def stepClass2 = (new Step() {}).getClass() as Class<? extends Step>
            configurer.step(stepClass1)
            configurer.step(stepClass2)
        when:
            def configuration = configurer.provide()
        then:
            configuration.stepsDefinitions.size() == 2
            configuration.stepsDefinitions == [StepDefinition.fromClass(stepClass1), StepDefinition.fromClass(stepClass2)]
    }

    def "should add steps configuration when step instances provided"() {
        setup:
        def configurer = configurerWithInitialNon()
        def stepInstance1 = new Step() {}
        def stepInstance2 = new Step() {}
        configurer.step(stepInstance1)
        configurer.step(stepInstance2)
        when:
        def configuration = configurer.provide()
        then:
        configuration.stepsDefinitions.size() == 2
        configuration.stepsDefinitions == [StepDefinition.fromInstance(stepInstance1), StepDefinition.fromInstance(stepInstance2)]
    }

    def "should fail when trying to set NULL step class"() {
        setup:
            def configurer = configurerWithInitialNon()
            def nullStep = null as Step
        when:
            configurer.step(nullStep)
        then:
            thrown NullPointerException
    }

    def "should fail when trying to set NULL step instance"() {
        setup:
            def configurer = configurerWithInitialNon()
            def nullStep = null as Class<? extends Step>
        when:
            configurer.step(nullStep)
        then:
            thrown NullPointerException
    }

    def "should fail when trying to set NULL step factory"() {
        setup:
            def configurer = configurerWithInitialNon()
        when:
            configurer.stepFactory(null)
        then:
            thrown NullPointerException
    }


    def "should fail when trying to set NULL exception handler"() {
        setup:
            def configurer = configurerWithInitialNon()
        when:
            configurer.exceptionHandler(null)
        then:
            thrown NullPointerException
    }

    private def configurerWithInitialNon() {
        final def configurer = new SequencerBuildConfigurer()
        configurer.initial(Initial.non())
        return configurer
    }
}
