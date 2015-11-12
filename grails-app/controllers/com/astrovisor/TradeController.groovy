package com.astrovisor

import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE
import static org.springframework.http.HttpStatus.CREATED

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TradeController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    TradeService tradeService

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index(Integer max) {
        max = Math.min(max ?: 10, 100)
        int offset = params.offset ?: 0
        def trades = []
        if(params.planet) {
            trades = tradeService.getTradesOfPlanet(params.planet, offset, max)
        }
        else {
            trades = tradeService.getTrades(offset, max)
        }
        respond trades, [status: OK]
    }

    @Transactional
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def save(Trade tradeInstance) {
        if (tradeInstance == null) {
            render status: NOT_FOUND
            return
        }

        tradeInstance.validate()
        if (tradeInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        tradeInstance = tradeService.insertOrUpdate(tradeInstance)
        respond tradeInstance, [status: CREATED]
    }

    @Transactional
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def update(Trade tradeInstance) {
        if (tradeInstance == null) {
            render status: NOT_FOUND
            return
        }

        tradeInstance.validate()
        if (tradeInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        tradeInstance = tradeService.insertOrUpdate(tradeInstance)
        respond tradeInstance, [status: OK]
    }

    @Transactional
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def delete(Trade tradeInstance) {

        if (tradeInstance == null) {
            render status: NOT_FOUND
            return
        }

        tradeService.delete(tradeInstance)
        render status: NO_CONTENT
    }
}
