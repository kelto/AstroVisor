package com.astrovisor

import spock.lang.*

/**
 *
 */
class TradeServiceSpec extends Specification {

    TradeService tradeService

    void "test save or update"() {
        given:"A trade"
            def orbit = Orbit.build(semimajor_axis:10000, semiminor_axis:10000, orbital_speed:50.0, revolution_period:365)
            def planet = Planet.build(orbit:orbit)
            def trade = new Trade(name: "trade", planet: planet)

        when:
            trade.validate()
            tradeService.insertOrUpdate(trade)

        then:
            Trade.count() == 1
    }

    void "test delete"() {
        given:
            def trade = new Trade(name: "trade")

        when:
            trade = tradeService.insertOrUpdate(trade)
            tradeService.delete(trade)

        then:
            Trade.count() == 0
    }

    void "test get trades of a planet"() {
        given:
            def orbit = Orbit.build(semimajor_axis:10000, semiminor_axis:10000, orbital_speed:50.0, revolution_period:365)
            def planet = Planet.build(orbit:orbit)
            (1..5).collect { Trade.build(planet: planet) }

        when:
            def trades = tradeService.getTradesOfPlanet(planet.id, 0, 10)

        then:
            trades.size() == 5
        when:
            trades = tradeService.getTradesOfPlanet(planet.id, 2, 10)

        then:
            trades.size() == 3
        when:
            trades = tradeService.getTradesOfPlanet(planet.id, 0, 4)

        then:
            trades.size() == 4
    }

    void "test get trades"() {
        given:
            def orbit = Orbit.build(semimajor_axis:10000, semiminor_axis:10000, orbital_speed:50.0, revolution_period:365)
            def planet = Planet.build(orbit:orbit)
            (1..10).collect { Trade.build(planet: planet) }

        when:
            def trades = tradeService.getTrades(0, 8)

        then:
            trades.size() == 8
        when:
            trades = tradeService.getTrades(6, 10)

        then:
            trades.size() == 4
    }
}
