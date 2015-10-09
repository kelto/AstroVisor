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
            def planet = new Planet(code_name: "XO-000",age:0, name: 'kelto',
                                    image: "image", description: "description",
                                    type: GAS)
            planet.save()
            def trade = new Trade(name: "trade", planet: planet)

        when:
            trade.validate()
            println trade.errors
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
