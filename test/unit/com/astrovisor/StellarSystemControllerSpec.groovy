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
@TestFor(StellarSystemController)
@Mock(StellarSystem)
class StellarSystemControllerSpec extends Specification {

    @Unroll
    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
        controller.index()

        then:"The response is correct"
        response.status == HttpStatus.OK.value
        response.text == ([] as JSON).toString()
    }
}
