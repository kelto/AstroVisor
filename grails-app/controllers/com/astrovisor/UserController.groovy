package com.astrovisor

import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UserController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT"]

    UserService userService

    @Transactional
    @Secured('IS_AUTHENTICATED_ANONYMOUSLY')
    def save(User user) {
        if(user == null) {
            render status: NOT_FOUND
            return
        }

        if(!user.validate()) {
            render status: NOT_ACCEPTABLE
            return
        }

        userService.insertOrUpdate(user)
        respond user, [status: CREATED]
    }

    @Transactional
    @Secured('IS_AUTHENTICATED_ANONYMOUSLY')
    def update(User user) {
        if(user == null) {
            render status: NOT_FOUND
            return
        }

        if(!user.validate()) {
            render status: NOT_ACCEPTABLE
            return
        }

        userService.insertOrUpdate(user)
        respond user, [status: OK]
    }
}
