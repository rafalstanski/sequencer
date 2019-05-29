package com.hurricane.components.sequencer

import com.hurricane.components.sequencer.configure.Initializer
import com.hurricane.components.sequencer.configure.SequencerBuilder
import com.hurricane.components.sequencer.runtime.Sequencer
import com.hurricane.components.sequencer.runtime.SequencerResult
import com.hurricane.components.sequencer.runtime.Step
import spock.lang.Specification

class Scenario extends Specification {
    protected static Sequencer standalone(final Class<Step>... steps) {
        def builder = SequencerBuilder.standalone()
        if (steps.length > 0) {
            builder.start(steps[0])
            steps.drop(1).each {builder.next(it)}
        }

        return builder.build()
    }

    protected static Sequencer initializedBy(final String artifactName, final Class<Step>... steps) {
        def builder = SequencerBuilder.initializedBy(Initializer.string(artifactName))
        if (steps.length > 0) {
            builder.start(steps[0])
            steps.drop(1).each {builder.next(it)}
        }

        return builder.build()
    }

    protected static SequencerResultAssertionWrapper assertResult(final SequencerResult result) {
        new SequencerResultAssertionWrapper(result)
    }
}


class SequencerResultAssertionWrapper {
    final SequencerResult result

    SequencerResultAssertionWrapper(final SequencerResult result) {
        this.result = result
    }

    void successful() {
        assert result.success()
        assert result.getExecutedSteps() == result.getSteps()
    }

    void artifactNumber(final int number) {
        assert result.artifacts.size() == number
    }

    void artifactWithValue(final String artifactName, final String expectedValue) {
        final foundArtifact = result.artifacts.find { it.name() == artifactName }

        assert foundArtifact
        assert foundArtifact.value == expectedValue
    }

    void noArtifact(final String artifactName) {
        final foundArtifact = result.artifacts.find { it.name() == artifactName }

        assert !foundArtifact, 'there shouldn\'t be an artifact with name: ' + artifactName
    }

    void hasArtifact(final String artifactName) {
        final foundArtifact = result.artifacts.find { it.name() == artifactName }

        assert foundArtifact, 'should found artifact with name: ' + artifactName
    }

    void failed() {
        assert !result.success()
        assert !result.errors.isEmpty()
    }

    void errorStepWithCause(final String stepName, final Throwable expectedCause) {
        final error = result.errors.find { it.stepName == stepName }

        assert error
        assert error.cause == expectedCause
    }

    void definedSteps(final String ... names) {
        assert result.steps == names.toList()
    }

    void executedSteps(final String ... names) {
        assert result.executedSteps == names.toList()
    }
}