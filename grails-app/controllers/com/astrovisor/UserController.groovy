package com.astrovisor

import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.UNAUTHORIZED

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UserController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT"]

    UserService userService;

    @Transactional
    @Secured('IS_AUTHENTICATED_ANONYMOUSLY')
    def me() {
        respond userService.currentUser(), [status: OK]
    }

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
    def update() {

        def params = request.JSON;
        if(!userService.checkPassword(params.password)) {
            render status: UNAUTHORIZED
            return
        }

        User user = userService.updateUser(params.user);
        if(!user) {
            render status: NOT_ACCEPTABLE
            return
        }

        render status: OK
        return
    }
}
