package com.astrovisor

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.http.HttpStatus.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(UserController)
@Mock([User, UserService])
class UserControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        params['username'] = "test"
        params['password'] = "password"
    }

    void "test save ok"() {
        given:
            populateValidParams(params)
            controller.request.json = params
            controller.request.method = 'POST'
            def serviceMock = mockFor(UserService)
            serviceMock.demand.insertOrUpdate {}
            controller.userService = serviceMock.createMock()
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
            populateValidParams(params)
            controller.request.json = ['username':'username']
            controller.request.method = 'POST'

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
            User user = new User()
            controller.update(user)

        then:"The response status is NOT_ACCEPTABLE"
            response.status == NOT_ACCEPTABLE.value

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            user = new User(params).save(flush: true)

            def serviceMock = mockFor(UserService)
            serviceMock.demand.insertOrUpdate {}
            controller.userService = serviceMock.createMock()
            controller.update(user)

        then:"The response status is OK and the updated instance is returned"
            response.status == OK.value
    }
}
