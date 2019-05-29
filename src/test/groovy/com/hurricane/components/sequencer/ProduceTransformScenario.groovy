package com.hurricane.components.sequencer

class ProduceTransformScenario extends Scenario {

    def 'should transform produced value'() {
        given:
            final sequencer = standalone(Produce1, Transform1)

        when:
            def result = sequencer.start()

        then:
            assertResult result with {
                successful()
                artifactWithValue('artifact1', 'artifact1_value_transformed')
            }
    }
}