package com.astrovisor



import spock.lang.*

/**
 *
 */
class UserServiceSpec extends Specification {

    UserService userService

    void "test save or update"() {
        given:
            def user = new User(username: "user", password: "password")

        when:
            user.validate()
            println user.errors
            userService.insertOrUpdate(user)

        then:
            User.count() == 1
    }
}
