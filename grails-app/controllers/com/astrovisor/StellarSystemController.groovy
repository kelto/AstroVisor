package com.astrovisor

import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE
import static org.springframework.http.HttpStatus.CREATED

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

@Transactional(readOnly = true)
class StellarSystemController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    StellarSystemService stellarSystemService

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index(StellarSystem system) {
        if(system)
            respond system, [status: OK]
        int max = Math.min(params.max ?: 10, 100)
        int offset = params.offset ?: 0
        def systems = stellarSystemService.getStellarSystems(offset, max)
        respond systems, [status: OK]
    }

    @Transactional
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def save(StellarSystem stellarSystemInstance) {
        if (stellarSystemInstance == null) {
            render status: NOT_FOUND
            return
        }

        stellarSystemInstance.validate()
        if (stellarSystemInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        stellarSystemInstance = stellarSystemService.insertOrUpdate(stellarSystemInstance)
        respond stellarSystemInstance, [status: CREATED]
    }

    @Transactional
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def update(StellarSystem stellarSystemInstance) {
        if (stellarSystemInstance == null) {
            render status: NOT_FOUND
            return
        }

        stellarSystemInstance.validate()
        if (stellarSystemInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        stellarSystemInstance = stellarSystemService.insertOrUpdate(stellarSystemInstance)
        respond stellarSystemInstance, [status: OK]
    }

    @Transactional
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def delete(StellarSystem stellarSystemInstance) {

        if (stellarSystemInstance == null) {
            render status: NOT_FOUND
            return
        }

        stellarSystemService.delete(stellarSystemInstance)
        render status: NO_CONTENT
    }
}
