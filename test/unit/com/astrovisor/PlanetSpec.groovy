package com.astrovisor

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import grails.buildtestdata.mixin.Build
import spock.lang.*

import static com.astrovisor.Planet.Type.*
import static com.astrovisor.Planet.Size.*

@TestFor(Planet)
@Mock([Orbit, StellarSystem])
@Build([Orbit])
class PlanetSpec extends Specification {

    //todo : change last row planet_age to 100, should still pass.
    @Unroll
    void "test invalid planet"() {
        given : "An invalid planet"
            Planet planet = new Planet(code_name: planet_code_name, age: planet_age,
                                       name: planet_name, texture: image, type: type,
                                       system: system, orbit: orbit)

        when: "We try to validate"
            def isValid = planet.validate()

        then: "The validation fail"
            isValid == false

        where:
            planet_code_name | planet_age | planet_name | image  | type | system                | orbit       | size
            ""               | 0          | null        | null   | GAS  | Mock(StellarSystem)   | Mock(Orbit) | SMALL
            "XO-344"         | -1         | null        | null   | GAS  | Mock(StellarSystem)   | Mock(Orbit) | SMALL
            null             | 100        | null        | null   | GAS  | Mock(StellarSystem)   | Mock(Orbit) | SMALL
            "XO-355"         | -100       | ""          | ""     | GAS  | Mock(StellarSystem)   | Mock(Orbit) | SMALL
            "XO-355"         | -100       | ""          | ""     | null | Mock(StellarSystem)   | Mock(Orbit) | SMALL
            "XO-355"         | -100       | ""          | ""     | null | Mock(StellarSystem)   | Mock(Orbit) | SMALL
            "XO-355"         | -100       | ""          | ""     | null | Mock(StellarSystem)   | Mock(Orbit) | SMALL
            "XO-355"         | -100       | ""          | ""     | GAS  | null                  | Mock(Orbit) | SMALL
            "XO-355"         | 100        | "planet"    | "img"  | GAS  | null                  | null        | SMALL
            "XO-355"         | 100        | "planet"    | "img"  | GAS  | null                  | null        | null

    }

    @Unroll
    void "test valid planet" () {
        given: "A valid planet"
            def orbit = Orbit.build()
            Planet planet = new Planet(code_name: planet_code_name, age: planet_age,
                                       name: planet_name, texture: texture, type: type,
                                       system: Mock(StellarSystem), orbit: orbit,
                                       size: SMALL)

        when: "We try to validate"
            def isValid = planet.validate()
            println planet.errors

        then: "The validation fail"
            isValid == true

        where:
            planet_code_name | planet_age | planet_name   | texture | type
            "Test"           | 0          | null          | "image" | GAS
            "XO-345"         | 1          | "HelloPlanet" | "image" | GAS
            "XA-033"         | 100        | null          | "image" | GAS
            "XO-356"         | 100        | "ByePlanet"   | "image" | TELLURIC
    }
}
