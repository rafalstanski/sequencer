package com.hurricane.components.sequencer.configure.invoker

import com.hurricane.components.sequencer.configure.annotations.Artifact
import com.hurricane.components.sequencer.runtime.ArtifactDefinition
import com.hurricane.components.sequencer.runtime.Step
import spock.lang.Specification

import java.lang.reflect.Method

import static com.hurricane.components.sequencer.test.IsAnArtifact.toBeAnArtifact
import static org.hamcrest.Matchers.empty
import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.Matchers.is
import static spock.util.matcher.HamcrestSupport.expect
import static spock.util.matcher.HamcrestSupport.that

class ConsumedArtifactsFinderSpec extends Specification {
    def finder = new ConsumedArtifactsFinder()

    def 'should fail when using null step\'s method'() {
        when:
            finder.find null
        then:
            thrown NullPointerException
    }

    def 'should return empty list of artifacts'() {
        when:
            def artifacts = finder.find noArguments()
        then:
            zero artifacts
    }

    def 'should return one artifact(String)'() {
        when:
            def artifacts = finder.find oneStringArgument()
        then:
            that artifacts, hasSize(1)
            expect artifacts[0], toBeAnArtifact('param_name', String)
    }

    def 'should return one primitive type artifact(int)'() {
        when:
            def artifacts = finder.find onePrimitiveTypeArgument()
        then:
            that artifacts, hasSize(1)
            expect artifacts[0], toBeAnArtifact('param_name', int)
    }

    def 'should return one artifact(array) when vargs'() {
        when:
            def artifacts = finder.find vArgs()
        then:
            that artifacts, hasSize(1)
            expect artifacts[0], toBeAnArtifact('param_name', String[])
    }

    def 'should return three different artifacts'() {
        when:
            def artifacts = finder.find threeDifferentTypesArguments()
        then:
            that artifacts, hasSize(3)
            expect artifacts[0], toBeAnArtifact('param_1', String)
            expect artifacts[1], toBeAnArtifact('param_2', Runnable)
            expect artifacts[2], toBeAnArtifact('param_3', char)
    }

    def 'should return three exactly the same artifacts'() {
        when:
            def artifacts = finder.find threeTheSameArguments()
        then:
            that artifacts, hasSize(3)
            expect artifacts[0], toBeAnArtifact('param_1', String)
            expect artifacts[1], toBeAnArtifact('param_1', String)
            expect artifacts[2], toBeAnArtifact('param_1', String)
    }

    private Method noArguments() {
        methodFrom new Step() {def process() {}}
    }

    private Method oneStringArgument() {
        methodFrom new Step() {def process(@Artifact("param_name") String name) {}}
    }

    private Method onePrimitiveTypeArgument() {
        methodFrom new Step() {def process(@Artifact("param_name") int name) {}}
    }

    private Method vArgs() {
        methodFrom new Step() {def process(@Artifact("param_name") String ... param) {}}
    }

    private Method threeDifferentTypesArguments() {
        methodFrom new Step() {def process(
                @Artifact("param_1") String param1,
                @Artifact("param_2") Runnable param2,
                @Artifact("param_3") char param3) {}}
    }

    private Method threeTheSameArguments() {
        methodFrom new Step() {def process(
                @Artifact("param_1") String param1,
                @Artifact("param_1") String param2,
                @Artifact("param_1") String param3) {}}
    }

    private Method methodFrom(final Step step) {
        Reflection.method(step, 'process')
    }

    private void zero(Collection<ArtifactDefinition> artifacts) {
        assert that(artifacts, is(empty()))
    }
}