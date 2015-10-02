package com.astrovisor

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.springframework.http.HttpStatus
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(DescriptionController)
@Mock(Description)
class DescriptionControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        params['text'] = "test"
        params['planet'] = Mock(Planet)
    }

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

        when:"The save action is executed with an invalid instance"
        // Make sure the domain class has at least one non-null property
        // or this test will fail.
        def description = new Description()
        controller.save(description)

        then:"The response status is NOT_ACCEPTABLE"
        response.status == HttpStatus.NOT_ACCEPTABLE.value

        when:"The save action is executed with a valid instance"
        response.reset()
        populateValidParams(params)
        description = new Description(params)

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
        populateValidParams(params)
        description = new Description(params).save(flush: true)
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
        populateValidParams(params)
        def description = new Description(params).save(flush: true)

        then:"It exists"
        Description.count() == 1

        when:"The domain instance is passed to the delete action"
        controller.delete(description)

        then:"The instance is deleted"
        Description.count() == 0
        response.status == HttpStatus.NO_CONTENT.value
    }
}
