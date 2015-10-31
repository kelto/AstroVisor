package com.astrovisor

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll
import grails.buildtestdata.mixin.Build

import static org.springframework.http.HttpStatus.*

import static com.astrovisor.Planet.Type.*

@TestFor(StellarSystemController)
@Mock([StellarSystem, StellarSystemService])
@Build([StellarSystem])
class StellarSystemControllerSpec extends Specification {

    @Unroll
    void "Test the index action returns the correct model"() {
        given:
            def serviceMock = mockFor(StellarSystemService)
            def stellarSystems = (1..5).collect { StellarSystem.build() }

        when:"The index action is executed"
            serviceMock.demand.getStellarSystems {offset, max -> stellarSystems}
            controller.stellarSystemService = serviceMock.createMock()
            controller.index()

        then:"The response is correct"
            response.status == OK.value
            response.text == (stellarSystems as JSON).toString()
    }


    void "test save ok"() {
        given:
            controller.request.json = StellarSystem.build()
            controller.request.method = 'POST'
            def serviceMock = mockFor(StellarSystemService)
            serviceMock.demand.insertOrUpdate {}
            controller.stellarSystemService = serviceMock.createMock()
        when:
            controller.save(null)

        then:"A NOT_FOUND is returned"
            response.status == NOT_FOUND.value

        when:
            controller.save()

        then:
           controller.response.status == CREATED.value
    }

    void "test save not ok"() {
        given:
            controller.request.json = new StellarSystem()
            controller.request.method = 'POST'
            def serviceMock = mockFor(StellarSystemService)
            serviceMock.demand.insertOrUpdate {}
            controller.stellarSystemService = serviceMock.createMock()

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
            StellarSystem stellarSystem = new StellarSystem()
            controller.update(stellarSystem)

        then:"The response status is NOT_ACCEPTABLE"
            response.status == NOT_ACCEPTABLE.value

        when:"A valid domain instance is passed to the update action"
            response.reset()
            stellarSystem = StellarSystem.build().save(flush: true)

            def serviceMock = mockFor(StellarSystemService)
            serviceMock.demand.insertOrUpdate {}
            controller.stellarSystemService = serviceMock.createMock()
            controller.update(stellarSystem)

        then:"The response status is OK and the updated instance is returned"
            response.status == OK.value
    }

    @Unroll
    void "Test that the delete action deletes an instance if it exists"() {
        given:"The delete action is called for a null instance"
            def serviceMock = mockFor(StellarSystemService)
            serviceMock.demand.delete {stellarSystem -> stellarSystem.delete(flush: true)}
            controller.stellarSystemService = serviceMock.createMock()
        when:
            controller.delete(null)

        then:"A NOT_FOUND is returned"
            response.status == NOT_FOUND.value

        when:"A domain instance is created"
            response.reset()
            def stellarSystem = StellarSystem.build().save(flush: true)

        then:"It exists"
            StellarSystem.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(stellarSystem)

        then:"The instance is deleted"
            StellarSystem.count() == 0
            response.status == NO_CONTENT.value
    }
}
