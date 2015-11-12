package com.astrovisor

import static com.astrovisor.Planet.Type.TELLURIC
import static com.astrovisor.Planet.Type.GAS
import static com.astrovisor.Planet.Size.SMALL
import static com.astrovisor.Planet.Size.LARGE
import static com.astrovisor.Planet.Size.XXL
import static com.astrovisor.Planet.Size.NORMAL
import static com.astrovisor.Planet.Size.XS

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class PlanetSpec extends Specification {
    def orbit

    def setup() {
        mockForConstraintsTests(Planet, [new Planet(code_name: '123456')])
        orbit = new Orbit(semimajor_axis:10000, semiminor_axis:10000, orbital_speed:50.0, revolution_period:365)
    }

    void "test invalid planet"() {
        given : "An invalid planet"
        Planet planet = new Planet(code_name: planet_code_name, age: planet_age,
                                   name: planet_name, texture: texture, type: type, size:size, orbit:orbit, system: new StellarSystem())

        when: "We try to validate"
        def isValid = planet.validate()

        then: "The validation fail"
        isValid == false

        where:
        planet_code_name | planet_age  | planet_name | texture | type      | size
        "123456"         | 0           | "mars"      | "mars"  | TELLURIC  | SMALL
        "X0-344"         | -1          | "mars"      | "mars"  | TELLURIC  | SMALL
        "X0-344"         | 15000000000 | "mars"      | "mars"  | TELLURIC  | SMALL
        "X0-344"         | 13000000000 | "mars"      | "mars"  | ""        | SMALL
        "X0-344"         | 13000000000 | "mars"      | "mars"  | null      | SMALL
        "X0-344"         | 150000      | "mars"      | "mars"  | TELLURIC  | ""
        "X0-344"         | 150000      | "mars"      | "mars"  | TELLURIC  | null
    }

    void "test valid planet" () {
        given: "A valid planet"
        Planet planet = new Planet(code_name: planet_code_name, age: planet_age,
                                   name: planet_name, texture: texture, type: type, size:size, orbit:orbit, system: new StellarSystem())

        when: "We try to validate"
        def isValid = planet.validate()

        then: "The validation fail"
        isValid == true

        where:
        planet_code_name | planet_age       | planet_name   | texture | type      | size
        "X0-000"         | 13700000000      | "La Terre"    | "earth" | TELLURIC  | NORMAL
        "Test"           | 0                | null          | "image" | GAS       | XS
        "XO-345"         | 1                | "HelloPlanet" | "image" | GAS       | LARGE
        "XA-033"         | 100              | null          | "image" | GAS       | SMALL
        "XO-356"         | 100              | "ByePlanet"   | "image" | TELLURIC  | XXL
    }
}
