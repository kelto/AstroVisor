package com.astrovisor

import static com.astrovisor.Planet.Type.*

import spock.lang.*

class DescriptionServiceSpec extends Specification {

    DescriptionService descriptionService

    void "test get descriptions of a planet"() {
        given:
            def planet = Planet.build()
            (1..5).collect { Description.build(planet: planet) }

        when:
            def descriptions = descriptionService.getDescriptionsOfPlanet(planet.id, 0, 10)

        then:
            descriptions.size() == 5
        when:
            descriptions = descriptionService.getDescriptionsOfPlanet(planet.id, 2, 10)

        then:
            descriptions.size() == 3
        when:
            descriptions = descriptionService.getDescriptionsOfPlanet(planet.id, 0, 4)

        then:
            descriptions.size() == 4
    }

    void "test get descriptions"() {
        given:
            def planet = Planet.build()
            def trade = Trade.build()
            (1..5).collect { Description.build(planet: planet) }
            (1..5).collect { Description.build(trade: trade) }

        when:
            def descriptions = descriptionService.getDescriptions(0, 8)

        then:
            descriptions.size() == 8
        when:
            descriptions = descriptionService.getDescriptions(6, 10)

        then:
            descriptions.size() == 4
    }
}
