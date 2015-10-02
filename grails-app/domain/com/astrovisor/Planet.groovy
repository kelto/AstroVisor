package com.astrovisor

class Planet {

    String code_name
    String name
    int age


    static constraints = {
        code_name blank: false, unique: true, nullable: false
        name blank: false, nullable: true
        age min: 0
    }
}