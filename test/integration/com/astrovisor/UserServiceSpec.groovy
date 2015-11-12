package com.astrovisor

import grails.plugin.springsecurity.SpringSecurityUtils
import spock.lang.Specification

import static com.astrovisor.Climate.ClimateType.DRY
import static com.astrovisor.Planet.Size.SMALL
import static com.astrovisor.Planet.Type.TELLURIC

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

    void "test not authenticated currentUser"() {
        when:
            userService.currentUser()
        then:
            thrown(Exception)

    }

    void "test totalVoteOfUser"() {
        given:
            def user = new User(username: "user", password: "password")
            user = user.save()
            SpringSecurityUtils.reauthenticate("user","password")
            def system = new StellarSystem(code_name:"S0-001", name:"Alpha Centauri")
            system.save(failOnError: true, flush: true, insert: true)
            def climate = new Climate(minTemp:-183, maxTemp:427, meanTemp:169, seasons:1, type:DRY)
            def orbit = new Orbit(semimajor_axis:10000, semiminor_axis:10000, orbital_speed:50.0, revolution_period:365);
            def planet = new Planet(age:4000000000, name: 'Mercure',
                texture: "mercury", description:"description", system:system, orbit:orbit,
                type: TELLURIC, size: SMALL, atmosphere: false, climate:climate)
            planet.save(failOnError:true, flush:true, insert: true)

            Description description = new Description(text:"test desc",planet:planet,user:user,upvotes:4, downvotes:2)
            description.save(failOnError: true,flush:true,insert:true)
        when:
            int total = userService.totalVoteOfUser(user)

        then:
            total == 2
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
