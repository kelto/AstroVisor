package com.astrovisor

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.springframework.http.HttpStatus
import spock.lang.Specification
import spock.lang.Unroll
import grails.buildtestdata.mixin.Build

import static com.astrovisor.Planet.Type.*

@TestFor(PlanetController)
@Mock([StellarSystem,, Planet])
@Build([Planet, StellarSystem])
class PlanetControllerSpec extends Specification {

    @Unroll
    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The response is correct"
            response.status == HttpStatus.OK.value
            response.text == ([] as JSON).toString()
    }

    @Unroll
    void "Test the save action correctly persists an instance"() {
        given:
            def system = StellarSystem.build()
        when:
            controller.save(null)

        then:"A NOT_FOUND is returned"
            response.status == HttpStatus.NOT_FOUND.value

        when:"The save action is executed with an invalid instance"
            def planet = new Planet()
            controller.save(planet)

        then:"The response status is NOT_ACCEPTABLE"
            response.status == HttpStatus.NOT_ACCEPTABLE.value

        when:"The save action is executed with a valid instance"
            response.reset()
            planet = Planet.build(system: system)

            planet.validate()
            controller.save(planet)

        then:"The response status is CREATED and the instance is returned"
            response.status == HttpStatus.CREATED.value
            response.text == (planet as JSON).toString()
    }

    @Unroll
    void "Test the update action performs an update on a valid domain instance"() {
        given:
            def system = StellarSystem.build()
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"The response status is NOT_FOUND"
            response.status == HttpStatus.NOT_FOUND.value

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def planet = new Planet()
            controller.update(planet)

        then:"The response status is NOT_ACCEPTABLE"
            response.status == HttpStatus.NOT_ACCEPTABLE.value

        when:"A valid domain instance is passed to the update action"
            response.reset()
            planet = Planet.build(system: system).save(flush: true)
            controller.update(planet)

        then:"The response status is OK and the updated instance is returned"
            response.status == HttpStatus.OK.value
            response.text == (planet as JSON).toString()
    }

    @Unroll
    void "Test that the delete action deletes an instance if it exists"() {
        given:
            def system = StellarSystem.build()
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A NOT_FOUND is returned"
            response.status == HttpStatus.NOT_FOUND.value

        when:"A domain instance is created"
            response.reset()
            def planet = Planet.build(system: system).save(flush: true)

        then:"It exists"
            Planet.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(planet)

        then:"The instance is deleted"
            Planet.count() == 0
            response.status == HttpStatus.NO_CONTENT.value
    }

}
