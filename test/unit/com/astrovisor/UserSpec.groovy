package com.astrovisor

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class UserSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test a valid user"(String username, String password) {
        given: "A valid username and password"
        
        User user = new User(username: username, password: password)

        expect: "The user is valid"
        user.validate()

        where:
        
        username | password
        "user"   | "password"
    }

    void "test a invalid user"(String username, String password) {
        given: "A invalid username and password"
        
        User user = new User(username: username, password: password)

        expect: "The user is invalid"
        !user.validate()

        where:
        
        username | password
        "shr"    | "password"
        null     | "password"
        ""       | "password"
        "user"   | "pass"
        "user"   | null
        "user"   | ""
    }

    void "test a username is unique"() {
        given: "A user saved"

        User user = new User(username:"user", password: "password")

        mockForConstraintsTests(User, [user])

    
        User user2 = new User(username:"user", password: "password")

        expect: "The second user is invalid"
        !user2.validate()
    }
}
