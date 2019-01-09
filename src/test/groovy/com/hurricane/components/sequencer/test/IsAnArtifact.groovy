package com.hurricane.components.sequencer.test

import com.hurricane.components.sequencer.runtime.ArtifactDefinition
import org.hamcrest.Description
import org.hamcrest.Factory
import org.hamcrest.TypeSafeMatcher

class IsAnArtifact extends TypeSafeMatcher<ArtifactDefinition> {
    final String name
    final Class type

    IsAnArtifact(String name, Class type) {
        this.name = name
        this.type = type
    }

    @Override
    protected boolean matchesSafely(final ArtifactDefinition item) {
        item.name == name && item.supportedType == type
    }

    @Override
    void describeTo(Description description) {
        description.appendText(ArtifactDefinition.of(name, type).toString())
                .appendText(' / an artifact definition named ')
                .appendText(name)
                .appendText(' and supported type ')
                .appendValue(type)
    }

    @Factory
    static IsAnArtifact toBeAnArtifact(String name, Class type) {
        new IsAnArtifact(name, type)
    }

    @Factory
    static IsAnArtifact anArtifact(String name, Class type) {
        new IsAnArtifact(name, type)
    }
}
