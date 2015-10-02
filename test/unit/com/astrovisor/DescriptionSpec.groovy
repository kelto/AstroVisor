package com.astrovisor

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Description)
class DescriptionSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test invalid Description"() {
        given: "An invalid Description and a Planet"
        Description description = new Description(text: desc_text, planet:desc_planet)

        when: "We try to validate"
        def isValid = description.validate()

        then: "The validation fail"
        isValid == false

        where:
        desc_text | desc_planet
        ""        | Mock(Planet)
        null      | Mock(Planet)
        "yop"     | null
        "   "     | null
    }

    void "test valid Description"() {
        given: "A valid description"
        Description description = new Description(text: desc_text, planet: desc_planet)

        when: "We try to validate"
        def isValid = description.validate()

        then: "The validation succeed"
        isValid == true

        where:
        desc_text | desc_planet
        "plop"        | Mock(Planet)

    }
}
