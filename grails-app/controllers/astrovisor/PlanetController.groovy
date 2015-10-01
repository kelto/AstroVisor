package astrovisor



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PlanetController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Planet.list(params), [status: OK]
    }

    @Transactional
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
    def delete(Planet planetInstance) {

        if (planetInstance == null) {
            render status: NOT_FOUND
            return
        }

        planetInstance.delete flush:true
        render status: NO_CONTENT
    }
}
