package com.astrovisor

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@Mock([User, Role])
class UserRoleSpec extends Specification {

    void "test equals"() {
        given:
            def user1 = new User(username: "user", password: "password")
            def role1 = new Role(authority: "role")
            user1.save(flush: true)
            role1.save(flush: true)

            def user2 = new User(username: "user", password: "password")
            def role2 = new Role(authority: "role")
            user2.save(flush: true)
            role2.save(flush: true)

            def userRole1 = new UserRole(user: user1, role: role1)
            def userRole2 = new UserRole(user: user1, role: role1)
            def userRole3 = new UserRole(user: user1, role: role2)

        expect:
            userRole1.equals(userRole2)
            !userRole1.equals(userRole3)
    }

    void "test valid User role"() {
        given:
            def user = new User(username:"user", password: "password")
            user.save(flush: true)
            def role =  new Role(authority: "role")
            role.save(flush: true)
            UserRole userRole = new UserRole(user: user, role: role)

        expect:
            userRole.validate()
    }

    void "test create a new user role"() {
        given:
            def user = new User(username:"user", password: "password")
            def role = new Role(authority: "role")
            user.save(flush: true)
            role.save(flush: true)
        when:
            def userRole = UserRole.create(user, role, true)
        then:
            UserRole.exists(user.id, role.id)
    }

    void "test remove a user role"() {
        given:
            def user = new User(username:"user", password: "password")
            def role = new Role(authority: "role")
            user.save(flush: true)
            role.save(flush: true)
            def userRole = UserRole.create(user, role, true)
        when:
            UserRole.remove(user, role, true)
        then:
            !UserRole.exists(user.id, role.id)
    }

    void "test remove all roles of user"() {
        given:
            def user = new User(username:"user", password: "password")
            def role = new Role(authority: "role")
            user.save(flush: true)
            role.save(flush: true)
            def userRole = UserRole.create(user, role, true)
        when:
            UserRole.removeAll(user, true)
        then:
            user.getAuthorities().size() == 0
    }

    void "test remove all roles of user"() {
        given:
            def user = new User(username:"user", password: "password")
            def role = new Role(authority: "role")
            user.save(flush: true)
            role.save(flush: true)
            def userRole = UserRole.create(user, role, true)
        when:
            UserRole.removeAll(role, true)
        then:
           UserRole.where {role == role}.size() == 0
    }
}
