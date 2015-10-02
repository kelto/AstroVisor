package com.astrovisor

class Description {

    String text

    static belongsTo = [planet:Planet]

    static constraints = {
        text blank: false
    }
}
