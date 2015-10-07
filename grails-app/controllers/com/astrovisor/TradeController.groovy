package com.astrovisor

import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TradeController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    TradeService tradeService
    
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Trade.list(params), [status: OK]
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
