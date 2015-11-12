package com.astrovisor

import spock.lang.Specification

class UserIntegrationSpec extends Specification {

    void "test that password is encoded before insert"() {
        given:
        def user

        when:
            user = new User(username:"user1", password: "password")
            user.save(flush:true)
        then:
            user.password != "password"
    }

    void "test that password is encoded before update"() {
        given:
        def user

        when:
            user = new User(username:"user2", password: "password")
            user.save(flush:true)
            user.password = "new password"
            user.save(flush:true)
        then:
            user.password != "new password"
    }
}
