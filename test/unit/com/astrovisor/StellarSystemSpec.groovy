package com.astrovisor

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Unroll
import spock.lang.Specification

@TestMixin(GrailsUnitTestMixin)
class StellarSystemSpec extends Specification {

    @Unroll
    void "test invalid stellar system"() {
        given :
        StellarSystem system = new StellarSystem(code_name: code_name, name: name)

        when:
        def isValid = system.validate()

        then:
        isValid == false

        where:
        code_name | name
        ""        | "name"
        null      | "name"

    }

    void "test valid stellar system" () {
        given :
            StellarSystem system = new StellarSystem(code_name: code_name, name: name)

        when:
            def isValid = system.validate()

        then:
            isValid == true

        where:
            code_name  | name
            "XO-344"   | null
            "XO-344"   | "name"
    }
}
