package com.astrovisor

class Planet {

    public enum Type {
        TELLURIC, GAS
    }

    public enum Size {
        XS, SMALL, MEDIUM, NORMAL, LARGE, XL, XXL, XXXL
    }

    String code_name
    String name
    long age
    String texture
    String description
    boolean rings
    boolean atmosphere
    Climate climate
    String type
    String size

    static embedded = ['climate']

    static hasOne = [orbit:Orbit]

    static hasMany = [
        descriptions: Description,
        trades: Trade
    ]

    static belongsTo = [system:StellarSystem]
    static mapping = {
        descriptions cascade: 'all'
        trades: 'all'
    }

    static constraints = {
        code_name blank: false, unique: true, nullable: false
        name blank: true, nullable: true /*data binder will convert blank strings to null*/
        texture blank: false, nullable: false
        age validator: { val ->
            return val >= 0 && val < 14000000000
        }
        orbit nullable: false
        description nullable: true, blank: true
        climate nullable: true
        type blank: false, nullable: false
        size blank: false, nullable: false
    }
    /*Ref : http://grails.github.io/grails-doc/latest/ref/Constraints/nullable.html*/
}
