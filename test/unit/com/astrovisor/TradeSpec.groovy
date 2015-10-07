package com.astrovisor

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Trade)
class TradeSpec extends Specification {

    void "test trade is valid"() {
        given: "A trade with a valid name"
            Trade trade = new Trade(name: "trade")

        expect: "the trade is valid"
            trade.validate()
    }

    void "test trade is invalid"(String name) {
        given: "A trade with invalid name"
            Trade trade = new Trade(name: name)

        expect: "The trade is invalid"
            !trade.validate()

        where:
            name << ["", null]
    }
}
