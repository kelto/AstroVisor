package com.astrovisor

import static com.astrovisor.Planet.Type.*

import spock.lang.*

/**
 *
 */
class TradeServiceSpec extends Specification {

    TradeService tradeService

    void "test save or update"() {
        given:"A trade"
            def planet = Planet.build()
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
}
