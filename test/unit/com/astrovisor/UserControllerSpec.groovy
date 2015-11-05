package com.astrovisor

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

        when: "An invalid password is passed to the update action"
            response.reset()
            params.user = new User()
            params.password = "wrong password"
            def serviceMock = mockFor(UserService)
            serviceMock.demand.checkPassword { return false}
            controller.userService = serviceMock.createMock()
            controller.update()

        then: "The response status is UNAUTHORIZED"
            response.status == UNAUTHORIZED.value

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            params.user = new User()
            params.password = "password";
            serviceMock.demand.checkPassword { return true }
            serviceMock.demand.updateUser { return null }
            controller.userService = serviceMock.createMock()
            controller.update()

        then:"The response status is NOT_ACCEPTABLE"
            response.status == NOT_ACCEPTABLE.value

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            params.user = new User(params).save(flush: true)
            params.password = "password"
            serviceMock.demand.checkPassword { return true }
            serviceMock.demand.updateUser { return new User()}
            controller.userService = serviceMock.createMock()
            controller.update()

        then:"The response status is OK and the updated instance is returned"
            response.status == OK.value
    }
}
