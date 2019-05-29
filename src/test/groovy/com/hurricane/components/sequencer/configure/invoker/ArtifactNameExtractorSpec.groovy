package com.hurricane.components.sequencer.configure.invoker

import com.hurricane.components.sequencer.configure.annotations.Artifact
import com.hurricane.components.sequencer.exception.ConsumedArtifactsException
import spock.lang.Specification

import static org.hamcrest.Matchers.is
import static spock.util.matcher.HamcrestSupport.that

class ArtifactNameExtractorSpec extends Specification {
    def extractor = new ArtifactNameExtractor()

    def 'should fail when extracting from null parameter'() {
        when:
            extractor.extract null
        then:
            thrown NullPointerException
    }

    def 'should extract artifact\'s name from annotation'() {
        when:
            def name = extractor.extract annotated()
        then:
            that name, is('param_name')
    }

    def 'should fail when no annotation'() {
        when:
            extractor.extract nameUnavailable()
        then:
            thrown ConsumedArtifactsException
    }

    private def annotated() {
        resolveMethodParameter {@Artifact("param_name") String param -> }
    }

    private def nameUnavailable() {
        resolveMethodParameter {String paramName -> }
    }

    private def resolveMethodParameter(Closure target) {
        Reflection.parameter(target, 'call')
    }
}
