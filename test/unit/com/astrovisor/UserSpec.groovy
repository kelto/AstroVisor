package com.astrovisor

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@Mock([UserRole, Role])
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

    void "test get authorties"() {
        given: "A user with a role"
            User user = new User(username:"user", password: "password")
            user.save(flush: true)
            Role role = new Role(authority: "role")
            role.save(flush: true)
            UserRole userRole = new UserRole(user: user, role: role)
            userRole.save(flush: true)

        when:
            def roles = user.getAuthorities()

        then:
            roles.size() == 1
    }

    void "test that password is encoded before update"() {
        given:
            User user = new User(username: "user", password: "password")
            user.save(flush: true)
            user.springSecurityService = [encodePassword: { String passwd -> return "encoded password" },
                                          passwordEncoder: true]
        when:
            user.password = "new password"
            user.save(flush: true)
        then:
            user.password != "new password"
    }
}
