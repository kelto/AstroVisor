package com.astrovisor

import grails.transaction.Transactional

@Transactional
class DescriptionService {

    def getDescriptionsOfPlanet(long planetId, int offset, int max) {
        def planet = Planet.get(planetId)
        return Description.findAllByPlanet(planet, [offset: offset, max: max])
    }

    def getDescriptions(int offset, int max) {
        return Description.list([offset: offset, max:max])
    }

    def getDescriptionsOfTrade(long tradeId, int offset, int max) {
        def trade = Trade.get(tradeId)
        return Description.findAllByTrade(trade, [offset: offset, max: max])
    }
}
