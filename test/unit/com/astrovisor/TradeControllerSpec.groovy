package com.astrovisor

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.http.HttpStatus.*

import static com.astrovisor.Planet.Type.*

@TestFor(TradeController)
@Mock([Trade, TradeService, Planet])
class TradeControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        def planet = new Planet(code_name: "XO-000",age:0, name: 'kelto',
                                image: "image", description: "description",
                                type: GAS)
        planet.save(flush:true)
        params['planet'] = planet.id
        params['name'] = "trade"
    }

    @Unroll
    void "Test the index action returns the correct model"() {
        given:"A trade"
            populateValidParams(params)
            def trade = new Trade(params)
            trade.save(flush: true)

        when:"The index action is executed"
            controller.index()

        then:"The response is correct"
            response.status == OK.value
            response.text == ([trade] as JSON).toString()
    }


    void "test save ok"() {
        given:
            populateValidParams(params)
            controller.request.json = new Trade(params)
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
            trade = new Trade(params).save(flush: true)

            def serviceMock = mockFor(TradeService)
            serviceMock.demand.insertOrUpdate {}
            controller.tradeService = serviceMock.createMock()
            controller.update(trade)

        then:"The response status is OK and the updated instance is returned"
            response.status == OK.value
    }
}
