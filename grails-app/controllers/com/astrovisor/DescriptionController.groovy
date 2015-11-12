package com.astrovisor

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DescriptionController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    DescriptionService descriptionService

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index(int max) {
        max = Math.min(max ?: 10, 100)
        int offset = params.offset ?: 0
        def descriptions = [];
        if(params.planet)
            descriptions = descriptionService.getDescriptionsOfPlanet(params.planet, offset, max)
        else{
            if(params.trade)
                descriptions = descriptionService.getDescriptionsOfTrade(params.trade, offset, max)
            else
                descriptions = descriptionService.getDescriptions(offset, max)
        }
        respond descriptions, [status: OK]
    }

    @Transactional
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def save(Description description) {
        if (description == null) {
            render status: NOT_FOUND
            return
        }
        description.validate()
        if (description.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        description.save flush:true
        respond description, [status: CREATED]
    }

    @Transactional
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
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
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def delete(Description description) {
        if(description == null) {
            render status: NOT_FOUND
            return
        }

        description.delete flush: true
        render status: NO_CONTENT
    }
}
