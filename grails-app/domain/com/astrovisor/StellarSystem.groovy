package com.astrovisor

class StellarSystem {

    String code_name
    String name

    static hasMany = [planets: Planet]
    static constraints = {
        code_name blank: false, unique: true, nullable: false
        name nullable: true, blank: true
    }
}
