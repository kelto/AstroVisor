package com.astrovisor
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class PlanetController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index(Planet planet) {
        if(planet) {
            respond planet;
        } else {
            def max = Math.min(params.max ?: 10, 100)
            respond Planet.list(max: max), [status: OK]
        }
    }

    @Transactional
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def save(Planet planetInstance) {
        if (planetInstance == null) {
            render status: NOT_FOUND
            return
        }

        planetInstance.validate()
        if (planetInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        planetInstance.save flush:true
        respond planetInstance, [status: CREATED]
    }

    @Transactional
    def update(Planet planetInstance) {
        if (planetInstance == null) {
            render status: NOT_FOUND
            return
        }

        planetInstance.validate()
        if (planetInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        planetInstance.save flush:true
        respond planetInstance, [status: OK]
    }

    @Transactional
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def delete(Planet planetInstance) {

        if (planetInstance == null) {
            render status: NOT_FOUND
            return
        }

        planetInstance.delete flush:true
        render status: NO_CONTENT
    }
}
