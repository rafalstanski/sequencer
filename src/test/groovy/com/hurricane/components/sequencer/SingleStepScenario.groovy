package com.hurricane.components.sequencer

class SingleStepScenario extends Scenario {
    def 'should allow step that does not produce nor consume'() {
        given:
            final initArtifactName = 'init'
            final initArtifactValue = 'test value'
        and:
            final sequencer = initializedBy(initArtifactName, DevNull)

        when:
            final result = sequencer.start(initArtifactValue)

        then:
            assertResult result with {
                successful()
                artifactNumber(1)
                artifactWithValue(initArtifactName, initArtifactValue)
                definedSteps('devNull')
                executedSteps('devNull')
            }
    }

    def 'should consume initial artifact'() {
        given:
            final initArtifactName = 'artifact1'
            final initArtifactValue = 'test value'
        and:
            final sequencer = initializedBy(initArtifactName, Consume1)

        when:
            final result = sequencer.start(initArtifactValue)

        then:
            assertResult result with {
                successful()
                artifactNumber(1)
                artifactWithValue(initArtifactName, initArtifactValue)
                definedSteps('consume1')
                executedSteps('consume1')
            }
    }

    def 'should add artifact to result'() {
        given:
            final initArtifactName = 'init'
            final initArtifactValue = 'test value'
        and:
            final sequencer = initializedBy(initArtifactName, Produce1)

        when:
            final result = sequencer.start(initArtifactValue)

        then:
            assertResult result with {
                successful()
                artifactNumber(2)
                artifactWithValue(initArtifactName, initArtifactValue)
                artifactWithValue('artifact1', 'artifact1_value')
                definedSteps('produce1')
                executedSteps('produce1')
            }
    }

    def 'should take initial artifact and produce new one'() {
        given:
            final initArtifactName = 'artifact1'
            final initArtifactValue = 'test value'
        and:
            final sequencer = initializedBy(initArtifactName, Consume1Produce2)

        when:
            final result = sequencer.start(initArtifactValue)

        then:
            assertResult result with {
                successful()
                artifactNumber(2)
                artifactWithValue(initArtifactName, initArtifactValue)
                artifactWithValue('artifact2', 'changed_test value')
                definedSteps('consume1Produce2')
                executedSteps('consume1Produce2')
            }
    }

    def 'should fail when step throws and error'() {
        given:
            final initArtifactName = 'init'
            final initArtifactValue = 'test value'
        and:
            final sequencer = initializedBy(initArtifactName, Throw)

        when:
            final result = sequencer.start(initArtifactValue)

        then:
            assertResult result with {
                failed()
                errorStepWithCause('throw', Throw.WILL_THROW)
                artifactNumber(1)
                artifactWithValue(initArtifactName, initArtifactValue)
                definedSteps('throw')
                executedSteps('throw')
            }
    }
}
