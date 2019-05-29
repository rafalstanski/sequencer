package com.hurricane.components.sequencer

class SingleStepNoInitialScenario extends Scenario {
    def 'should allow step that does not produce nor consume (no initial)'() {
        given:
            final sequencer = standalone(DevNull)

        when:
            final result = sequencer.start()

        then:
            assertResult result with {
                successful()
                artifactNumber(0)
                definedSteps('devNull')
                executedSteps('devNull')
            }
    }

    def 'should add artifact to result (no initial)'() {
        given:
            final sequencer = standalone(Produce1)

        when:
            final result = sequencer.start()

        then:
            assertResult result with {
                successful()
                artifactNumber(1)
                artifactWithValue('artifact1', 'artifact1_value')
                definedSteps('produce1')
                executedSteps('produce1')
            }
    }

    def 'should fail when step throws and error (no initial)'() {
        given:
            final sequencer = standalone(Throw)

        when:
            final result = sequencer.start()

        then:
            assertResult result with {
                failed()
                artifactNumber(0)
                errorStepWithCause('throw', Throw.WILL_THROW)
                definedSteps('throw')
                executedSteps('throw')
            }
    }
}
