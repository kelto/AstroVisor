package com.astrovisor

import grails.plugin.springsecurity.SpringSecurityUtils
import spock.lang.Specification
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

    void "test checkPassword"() {
        given:
            def user = new User(username: "user", password: "password")
            user.save()
            SpringSecurityUtils.reauthenticate "user", "password"
        when:
            def passwordValid = userService.checkPassword("password")

        then:
            passwordValid == true

        when:
            passwordValid = userService.checkPassword("wrong")

        then:
            passwordValid == false
    }

    void "test currentUser"() {
        given:
            def user = new User(username: "user", password: "password")
            user = user.save()
            SpringSecurityUtils.reauthenticate("user","password")
        when:
            def currentUser = userService.currentUser()

        then:
            currentUser == user
    }

    void "test updateUser"() {
        given:
            def user = new User(username: "user", password: "password")
            user = user.save()
            SpringSecurityUtils.reauthenticate("user","password")
        when:
            def map = new HashMap()
            map.password = "password2"
            userService.updateUser(map)
            SpringSecurityUtils.reauthenticate("user","password2")

        then:
            userService.checkPassword("password2")
    }
}
