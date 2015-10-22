package com.astrovisor

class Planet {

    public enum Type {
        TELLURIC, GAS
    }

    String code_name
    String name
    int age
    String image
    String description
    boolean rings
    boolean atmosphere
    float size
    String region
    Orbit orbit
    Climate climate
    Type type

    static embedded = ['climate']

    static hasMany = [descriptions: Description,
                      trades: Trade
                     ]

    static mapping = {
        descriptions cascade: 'all'
        trades: 'all'
    }

    static constraints = {
        code_name blank: false, unique: true, nullable: false
        name blank: false, nullable: true
        image blank: false, nullable: false
        age min: 0
        region blank: false, nullable: false
        orbit nullable: true
        description nullable: true, blank: true
        climate nullable: true
        type nullable: false
    }
}
