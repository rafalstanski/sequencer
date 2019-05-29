package com.hurricane.components.sequencer

class ProduceCombineScenario extends Scenario {

    def 'should transform produced value'() {
        given:
            final sequencer = standalone(Produce1, Produce2, Combine1_2_Return3)

        when:
            def result = sequencer.start()

        then:
            assertResult result with {
                successful()
                artifactWithValue('artifact1', 'artifact1_value')
                artifactWithValue('artifact2', 'artifact2_value')
                artifactWithValue('artifact3', 'artifact1_value_artifact2_value')
            }
    }
}