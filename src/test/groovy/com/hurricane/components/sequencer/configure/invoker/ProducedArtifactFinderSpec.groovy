package com.hurricane.components.sequencer.configure.invoker

import com.hurricane.components.sequencer.configure.annotations.Artifact
import com.hurricane.components.sequencer.runtime.Step
import spock.lang.Specification

import java.lang.reflect.Method

import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty
import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent
import static com.hurricane.components.sequencer.test.IsAnArtifact.toBeAnArtifact
import static spock.util.matcher.HamcrestSupport.expect
import static spock.util.matcher.HamcrestSupport.that

class ProducedArtifactFinderSpec extends Specification {
    def finder = new ProducedArtifactFinder()

    def 'should find produced/returned artifact'() {
        when:
            def foundDefinition = finder.find producingArtifact()
        then:
            that foundDefinition, isPresent()
            expect foundDefinition.get(), toBeAnArtifact('artifactName', String)
    }

    def 'should find produced/returned artifact even when return void'() {
        when:
            def foundDefinition = finder.find producingVoidArtifact()
        then:
            that foundDefinition, isPresent()
            expect foundDefinition.get(), toBeAnArtifact('artifactName', void)
    }

    def 'should not find produced artifact'() {
        when:
            def foundDefinition = finder.find noArtifact()
        then:
            that foundDefinition, isEmpty()
    }


    private Method producingArtifact() {
        methodFrom new Step() {@Artifact('artifactName') String process() {''}}
    }

    private Method producingVoidArtifact() {
        methodFrom new Step() {@Artifact('artifactName') void process() {}}
    }
    
    private Method noArtifact() {
        methodFrom new Step() {def process() {}}
    }

    private Method methodFrom(final Step step) {
        Reflection.method(step, 'process')
    }
}


