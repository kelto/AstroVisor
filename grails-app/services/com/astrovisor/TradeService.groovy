package com.astrovisor

import grails.transaction.Transactional

@Transactional
class TradeService {

    Trade insertOrUpdate(Trade trade) {
        trade.save()
        return trade
    }

    void delete(Trade trade) {
        trade.delete()
    }

    def getTradesOfPlanet(long planetId, int offset, int max) {
        def planet = Planet.get(planetId)
        return Trade.findAllByPlanet(planet, [offset: offset, max: max])
    }

    def getTrades(int offset, int max) {
        return Trade.list([offset: offset, max:max])
    }
}
