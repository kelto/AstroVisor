package com.astrovisor

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

@Transactional(readOnly = true)
class DescriptionController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index(Description description) {
        if(description == null) {
            respond Description.list(), [status: OK]

        } else {
            respond description, [status: OK]
        }
    }

    @Transactional
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def save(Description description) {
        if (description == null) {
            render status: NOT_FOUND
            return
        }
//        def description = new Description(text: null)
        description.validate()
        if (description.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        description.save flush:true
        respond description, [status: CREATED]
    }

    @Transactional
    def update(Description description) {
        if(description == null) {
            render status: NOT_FOUND
            return
        }

        description.validate()
        if(description.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        description.save flush:true
        respond description, [status: OK]
    }

    @Transactional
    def delete(Description description) {
        if(description == null) {
            render status: NOT_FOUND
            return
        }

        description.delete flush: true
        render status: NO_CONTENT
    }
}
