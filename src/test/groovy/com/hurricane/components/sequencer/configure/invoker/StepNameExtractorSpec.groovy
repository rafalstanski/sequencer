package com.hurricane.components.sequencer.configure.invoker

import com.hurricane.components.sequencer.configure.annotations.Name
import com.hurricane.components.sequencer.runtime.NamedStep
import com.hurricane.components.sequencer.runtime.Step
import spock.lang.Specification

import static org.hamcrest.Matchers.is
import static spock.util.matcher.HamcrestSupport.that

class StepNameExtractorSpec extends Specification {
    def extractor = new StepNameExtractor()

    def 'should fail when extracting from null step'() {
        when:
            extractor.extract null
        then:
            thrown NullPointerException
    }

    def 'should extract from interface'() {
        when:
            def name = extractor.extract inherited()
        then:
            that name, is('inherited_name')
    }

    def 'should extract from annotation'() {
        when:
            def name = extractor.extract annotated()
        then:
            that name, is('annotated_name')
    }

    def 'should extract as class name'() {
        when:
            def name = extractor.extract pureStepClass()
        then:
            that name, is(ClassNameStep.getCanonicalName())
    }

    def 'should extract inherited first'() {
        when:
            def name = extractor.extract annotatedAndInherited()
        then:
            that name, is('inherited_name')
    }

    private NamedStep inherited() {
        new NamedStep() {String name() {'inherited_name'}}
    }

    private Step annotated() {
        new AnnotatedStep()
    }

    private Step annotatedAndInherited() {
        new AnnotatedAndInheritedStep()
    }

    private Step pureStepClass() {
        new ClassNameStep()
    }

    @Name('annotated_name')
    class AnnotatedStep implements Step {}

    class ClassNameStep implements Step {}

    @Name('annotated_name')
    class AnnotatedAndInheritedStep implements NamedStep {String name() {'inherited_name'}}

}
