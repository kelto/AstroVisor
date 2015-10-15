package com.astrovisor

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.springframework.http.HttpStatus
import spock.lang.Specification
import spock.lang.Unroll
import grails.buildtestdata.mixin.Build


/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(DescriptionController)
@Mock([Description, DescriptionService, Planet, Trade])
@Build([Planet, Trade, Description])
class DescriptionControllerSpec extends Specification {

    @Unroll
    void "Test the index action returns the correct model"() {
        given:
            def serviceMock = mockFor(DescriptionService)
            def planet = Planet.build()
            def planetDescriptions = (1..5).collect { Description.build(planet: planet) }
            def trade = Trade.build()
            def tradeDescriptions = (1..5).collect { Description.build(trade: trade) }

        when:"The index action is executed"
            serviceMock.demand.getDescriptions {offset, max -> []}
            controller.descriptionService = serviceMock.createMock()
            controller.index()

        then:"The response is correct"
            response.status == HttpStatus.OK.value
            response.text == ([] as JSON).toString()

        when:"The index action is executed"
            response.reset()
            serviceMock.demand.getDescriptionsOfPlanet {planetId, offset, max -> planetDescriptions}
            controller.descriptionService = serviceMock.createMock()
            params.planet = planet.id
            controller.index()

        then:"The response is correct"
            response.status == HttpStatus.OK.value
            response.text == (planetDescriptions as JSON).toString()

        when:"The index action is executed"
            response.reset()
            serviceMock.demand.getDescriptionsOfTrade {tradeId, offset, max -> tradeDescriptions}
            controller.descriptionService = serviceMock.createMock()
            params.planet = null
            params.trade = trade.id
            controller.index()

        then:"The response is correct"
            response.status == HttpStatus.OK.value
            response.text == (tradeDescriptions as JSON).toString()
    }

    @Unroll
    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def description = new Description()
            controller.save(description)

        then:"The response status is NOT_ACCEPTABLE"
            response.status == HttpStatus.NOT_ACCEPTABLE.value

        when:"The save action is executed with a valid instance"
            response.reset()
            def planet = Planet.build()
            description = Description.build(planet: planet)
            controller.save(description)

        then:"The response status is CREATED and the instance is returned"
            response.status == HttpStatus.CREATED.value
            response.text == (description as JSON).toString()
    }

    @Unroll
    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"The response status is NOT_FOUND"
            response.status == HttpStatus.NOT_FOUND.value

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def description = new Description()
            controller.update(description)

        then:"The response status is NOT_ACCEPTABLE"
            response.status == HttpStatus.NOT_ACCEPTABLE.value

        when:"A valid domain instance is passed to the update action"
            response.reset()
            def planet = Planet.build()
            description = Description.build(planet: planet)
            controller.update(description)

        then:"The response status is OK and the updated instance is returned"
            response.status == HttpStatus.OK.value
            response.text == (description as JSON).toString()
    }

    @Unroll
    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A NOT_FOUND is returned"
            response.status == HttpStatus.NOT_FOUND.value

        when:"A domain instance is created"
            response.reset()
            def planet = Planet.build()
            def description = Description.build(planet: planet)

        then:"It exists"
            Description.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(description)

        then:"The instance is deleted"
            Description.count() == 0
            response.status == HttpStatus.NO_CONTENT.value
    }
}
