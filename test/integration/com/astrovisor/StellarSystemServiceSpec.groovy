package com.astrovisor

import spock.lang.*

class StellarSystemServiceSpec extends Specification {

    StellarSystemService stellarSystemService

    void "test save or update"() {
        given:"A stellarSystem"
            def stellarSystem = StellarSystem.build()

        when:
            stellarSystem.validate()
            stellarSystemService.insertOrUpdate(stellarSystem)

        then:
            StellarSystem.count() == 1
    }

    void "test delete"() {
        given:
            def stellarSystem = StellarSystem.build()

        when:
            stellarSystem = stellarSystemService.insertOrUpdate(stellarSystem)
            stellarSystemService.delete(stellarSystem)

        then:
            StellarSystem.count() == 0
    }

    void "test get stellarSystems"() {
        given:
            (1..10).collect { StellarSystem.build() }

        when:
            def stellarSystems = stellarSystemService.getStellarSystems(0, 8)

        then:
            stellarSystems.size() == 8
        when:
            stellarSystems = stellarSystemService.getStellarSystems(6, 10)

        then:
            stellarSystems.size() == 4
    }
}
