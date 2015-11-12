package com.astrovisor

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Orbit)
class OrbitSpec extends Specification {

    Orbit orbit

    def setup() {
        orbit = new Orbit()
    }

    void "test an orbit validity"() {
        given:"an orbit"
        orbit.revolution_period = aPeriod
        orbit.semiminor_axis = aMinorAxis
        orbit.semimajor_axis = aMajorAxis
        orbit.orbital_speed = aSpeed

        when:"orbit is validated"
        def res = orbit.validate()

        then:"constraints are correctly applied"
        res == expectedState

        where:
        aPeriod  | aMinorAxis | aMajorAxis | aSpeed | expectedState
        0        | 3.0        | 400.1      | 550.0  | false
        2.0      | 0          | 400.1      | 550.0  | false
        2.0      | 3.0        | 0          | 550.0  | false
        2.0      | 3.0        | 400.1      | 0      | false
        2.0      | 3.0        | 400.1      | 550.0  | true
    }
}
