package com.astrovisor

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(StellarSystem)
class StellarSystemSpec extends Specification {

    StellarSystem system

    def setup() {
        mockForConstraintsTests(StellarSystem, [new StellarSystem(code_name: 'SYS-001')])
        system = new StellarSystem()
    }

    def cleanup() {
    }

    void "test a StellarSystem validity"() {
        given:"a stellar system"
        system.code_name = aCodeName
        system.name = aName

        when:"orbit is validated"
        def res = system.validate()

        then:"constraints are correctly applied"
        res == expectedState

        where:
        aName  | aCodeName | expectedState
        "Sol"  | "SYS-001" | false
        "Sol"  | ""        | false
        "Sol"  | null      | false
        ""     | "SYS-003" | true
        null   | "SYS-003" | true
    }
}
