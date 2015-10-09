package com.astrovisor

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

import static com.astrovisor.Planet.Type.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class PlanetSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    //todo : change last row planet_age to 100, should still pass.
    void "test invalid planet"() {
        given : "An invalid planet"
        Planet planet = new Planet(code_name: planet_code_name, age: planet_age, name: planet_name, image: image, type: type)

        when: "We try to validate"
        def isValid = planet.validate()

        then: "The validation fail"
        isValid == false

        where:
        planet_code_name | planet_age | planet_name | image  | type
        ""               | 0          | null        | null   | GAS
        "XO-344"         | -1         | null        | null   | GAS
        null             | 100        | null        | null   | GAS
        "XO-355"         | -100       | ""          | ""     | GAS
        "XO-355"         | -100       | ""          | ""     | null

    }

    void "test valid planet" () {
        given: "A valid planet"
        Planet planet = new Planet(code_name: planet_code_name, age: planet_age, name: planet_name, image: image, type: type)

        when: "We try to validate"
        def isValid = planet.validate()

        then: "The validation fail"
        isValid == true

        where:
        planet_code_name | planet_age | planet_name   | image   | type
        "Test"           | 0          | null          | "image" | GAS
        "XO-345"         | 1          | "HelloPlanet" | "image" | GAS
        "XA-033"         | 100        | null          | "image" | GAS
        "XO-356"         | 100        | "ByePlanet"   | "image" | TELLURIC
    }
}
