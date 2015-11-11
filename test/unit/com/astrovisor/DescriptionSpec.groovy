package com.astrovisor

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Description)
@Mock([Planet, Trade, User])
class DescriptionSpec extends Specification {

    @Unroll
    void "test invalid Description"() {
        given: "An invalid Description and a Planet"
        Description description = new Description(text: desc_text, planet: planet,
                                                  trade: trade, user:user)

        when: "We try to validate"
        def isValid = description.validate()

        then: "The validation fail"
        isValid == false

        where:
        desc_text | planet         | trade       | user
        ""        | Mock(Planet)   | Mock(Trade) | Mock(User)
        null      | Mock(Planet)   | Mock(Trade) | Mock(User)
        "   "     | Mock(Planet)   | Mock(Trade) | Mock(User)
        "text"    | null           | null        | Mock(User)
        "text"    | Mock(Planet)   | Mock(Trade) | Mock(User)
//        "text"    | Mock(Planet)   | null        | null
    }

    void "test valid Description"() {
        given: "A valid description"
        Description description = new Description(text: desc_text, planet: planet,
                                                  trade: trade, user:Mock(User))

        when: "We try to validate"
        def isValid = description.validate()

        then: "The validation succeed"
        isValid == true

        where:
        desc_text | planet       | trade
        "plop"    | Mock(Planet) | null
        "plop"    | null         | Mock(Trade)
    }
}
