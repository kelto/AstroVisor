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
}
