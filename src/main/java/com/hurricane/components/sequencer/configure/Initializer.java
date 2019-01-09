package com.hurricane.components.sequencer.configure;

import com.hurricane.components.sequencer.runtime.Initial;

public class Initializer {
    public static Initial<Void> non() {
        return Initial.non();
    }

    public static Initial<Integer> integer(final String artifactName) {
        return Initial.artifact(artifactName, Integer.class);
    }

    public static Initial<String> string(final String artifactName) {
        return Initial.artifact(artifactName, String.class);
    }

    public static <T> Initial<T> object(final String artifactName, Class<T> type) {
        return Initial.artifact(artifactName, type);
    }
}
