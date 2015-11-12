package com.astrovisor

import groovy.transform.ToString
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
@ToString(includeNames = true, includeFields = true, excludes = "metaClass,class")
class Trade {

    String name

    static belongsTo = [planet: Planet]

    static hasMany = [descriptions: Description]

    static mapping = {
        descriptions cascade: 'all'
    }

    static constraints = {
        name blank: false
    }
}
