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
    Size size
    Orbit orbit
    Climate climate
    Type type

    static embedded = ['climate', 'orbit']

    static hasMany = [descriptions: Description,
                      trades: Trade
                     ]

    static belongsTo = [system: StellarSystem]

    static mapping = {
        descriptions cascade: 'all'
        trades: 'all'
    }

    static constraints = {
        code_name blank: false, unique: true, nullable: false
        name blank: false, nullable: true
        texture blank: false, nullable: false
        age validator: { val ->
                return val >= 0 && val < 14000000000
        }
        orbit nullable: false
        description nullable: true, blank: true
        climate nullable: true
        type nullable: false
        size blank: false, nullable: false
    }
}
