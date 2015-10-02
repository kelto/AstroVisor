package com.astrovisor

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Climate)
class ClimateSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test a climate validity"(float aMinTemp, float aMaxTemp, float aMeanTemp, float nbSeasons, boolean expectedState) {
        given:"a climate"
        Climate climate = new Climate(aMinTemp, aMaxTemp, aMeanTemp, nbSeasons)

        when:"climate is validated"
        def res = climate.validate()

        then:"constraints are correctly applied"
        res == expectedState

        where:
        aMinTemp  | aMaxTemp | aMeanTemp | nbSeasons | expectedState
        -500      | 30       | 23        | 1         | false
        -20       | -500     | 23        | 1         | false
        -20       | -500     | -500      | 1         | false

        -20       | -10      | 23        | 1         | false
        -20       | -10      | -30       | 1         | false
        -20       | -50      | -30       | 1         | false
        -80       | 55       | 13        | 0         | false
    }
}