package com.hurricane.components.sequencer

import com.hurricane.components.sequencer.configure.SequencerBuilder

import static com.hurricane.components.sequencer.configure.Initializer.string
import static org.hamcrest.Matchers.hasSize
import static spock.util.matcher.HamcrestSupport.that

class NoStepsScenario extends Scenario {
    def 'should allow empty sequencer with no initial value'() {
        given:
            final sequencer = standalone()

        when:
            final result = sequencer.start()

        then:
            result.success()
            result.errors.isEmpty()
        and:
            result.artifacts.isEmpty()
            result.steps.isEmpty()
            result.executedSteps.isEmpty()
    }


    def 'for empty sequence should forward initial value'() {
        given:
            final initArtifactName = 'init'
            final initArtifactValue = 'test value'
        and:
            final sequencer = SequencerBuilder
                    .initializedBy(string(initArtifactName))
                    .build()

        when:
            final result = sequencer.start(initArtifactValue)

        then:
            result.success()
            result.errors.isEmpty()
        and:
            result.steps.isEmpty()
            result.executedSteps.isEmpty()
        and:
            that result.artifacts, hasSize(1)
            result.artifacts.first() with {
                value == initArtifactValue
                definition.name == initArtifactName
            }
    }
}
