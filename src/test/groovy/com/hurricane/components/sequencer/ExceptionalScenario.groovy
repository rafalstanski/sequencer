package com.hurricane.components.sequencer

import com.hurricane.components.sequencer.exception.SequencerDefinitionValidatorException

class ExceptionalScenario extends Scenario {
    def 'should fail when creating sequence with step expecting non existing artifact'() {
        when:
            initializedBy("some_name", Consume1Produce2)

        then:
            thrown SequencerDefinitionValidatorException
    }

    def 'should fail when creating sequence with step expecting non existing artifact (non initial)'() {
        when:
            standalone(Consume1Produce2)

        then:
            thrown SequencerDefinitionValidatorException
    }

    def 'should fail when same step used few times'() {
        when:
            standalone(Produce1, Produce1, Produce1)

        then:
            thrown SequencerDefinitionValidatorException
    }

    def 'should not invoke all steps because of an exception'() {
        given:
            final sequencer = standalone(Produce1, Produce2, Throw, Produce3)

        when:
            def result = sequencer.start()

        then:
            assertResult result with {
                artifactNumber(2)
                artifactWithValue('artifact1', 'artifact1_value')
                artifactWithValue('artifact2', 'artifact2_value')
                noArtifact('artifact3')
                errorStepWithCause('throw', Throw.WILL_THROW)
                definedSteps('produce1', 'produce2', 'throw', 'produce3')
                executedSteps('produce1', 'produce2', 'throw')
            }
    }
}
