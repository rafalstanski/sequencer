package com.hurricane.components.sequencer

class ProduceMultipleScenario extends Scenario {

    def 'should produce multiple artifacts'() {
        given:
            final sequencer = standalone(Produce1, Produce2, Produce3)

        when:
            def result = sequencer.start()

        then:
            assertResult result with {
                successful()
                hasArtifact('artifact1')
                hasArtifact('artifact2')
                hasArtifact('artifact3')
            }
    }
}
