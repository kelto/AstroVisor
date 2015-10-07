package com.astrovisor

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.http.HttpStatus.*

@TestFor(TradeController)
@Mock([Trade, TradeService])
class TradeControllerSpec extends Specification {
    
    def populateValidParams(params) {
        assert params != null
        params['name'] = "trade"
    }

    @Unroll
    void "Test the index action returns the correct model"() {
        given:"A trade"
            def trade = new Trade(name: "trade")
            trade.save(flush: true)

        when:"The index action is executed"
            controller.index()

        then:"The response is correct"
            response.status == OK.value
            response.text == ([trade] as JSON).toString()
    }


    void "test save ok"() {
        given: 
            controller.request.json = new Trade(name: "trade")
            controller.request.method = 'POST'
            def serviceMock = mockFor(TradeService)
            serviceMock.demand.insertOrUpdate {}
            controller.tradeService = serviceMock.createMock()

        when:
            controller.save()

        then:
           controller.response.status == CREATED.value
    }
    
    void "test save not ok"() {
        given: 
            controller.request.json = new Trade()
            controller.request.method = 'POST'
            def serviceMock = mockFor(TradeService)
            serviceMock.demand.insertOrUpdate {}
            controller.tradeService = serviceMock.createMock()

        when:
            controller.save()

        then:
           controller.response.status == NOT_ACCEPTABLE.value
    }

    @Unroll
    void "Test the update action"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"The response status is NOT_FOUND"
            response.status == NOT_FOUND.value

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            Trade trade = new Trade()
            controller.update(trade)

        then:"The response status is NOT_ACCEPTABLE"
            response.status == NOT_ACCEPTABLE.value

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            trade = new Trade(name: "name1").save(flush: true)
            
            def serviceMock = mockFor(TradeService)
            serviceMock.demand.insertOrUpdate {}
            controller.tradeService = serviceMock.createMock()
            controller.update(trade)

        then:"The response status is OK and the updated instance is returned"
            response.status == OK.value
    }
}
