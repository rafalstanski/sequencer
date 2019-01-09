package com.hurricane.components.sequencer.configure.invoker

import com.hurricane.components.sequencer.configure.annotations.Process
import com.hurricane.components.sequencer.exception.ProcessMethodMissingException
import com.hurricane.components.sequencer.runtime.Step
import spock.lang.Specification

import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.notNullValue
import static spock.util.matcher.HamcrestSupport.expect
import static spock.util.matcher.HamcrestSupport.that

class ProcessMethodFinderSpec extends Specification {
    def finder = new ProcessMethodFinder()

    def 'should fail when using null step'() {
        when:
            finder.find null
        then:
            thrown NullPointerException
    }


    def 'should find processing method'() {
        when:
            def foundMethod = finder.find oneProcessingMethod()
        then:
            expect foundMethod, notNullValue()
            that foundMethod.name, is('process')
    }

    def 'should find processing method within many others'() {
        when:
            def foundMethod = finder.find manyMethodsOneProcessingMethod()
        then:
            expect foundMethod, notNullValue()
            that foundMethod.name, is('withAnnotation')
    }

    def 'should fail when no processing method'() {
        when:
            finder.find noProcessingMethod()
        then:
            thrown ProcessMethodMissingException
    }

    def 'should fail when no method at all'() {
        when:
            finder.find emptyClass()
        then:
            thrown ProcessMethodMissingException
    }

    def 'should fail when to many processing methods'() {
        when:
            finder.find manyProcessingMethods()
        then:
            thrown ProcessMethodMissingException
    }

    private Step oneProcessingMethod() {
        new Step() {def @Process process() {}}
    }

    private Step manyMethodsOneProcessingMethod() {
        new Step() {
            def @Process withAnnotation() {}
            def noAnnotaiton() {}
            def andAnotherOne() {}
        }
    }

    private Step emptyClass() {
        new Step() {}
    }

    private Step noProcessingMethod() {
        new Step() {def process(){}}
    }

    private Step manyProcessingMethods() {
        new Step() {
            def @Process process1() {}
            def @Process process2() {}
            def @Process process3() {}
        }
    }
}
