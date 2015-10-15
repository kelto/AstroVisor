package com.astrovisor

class Description {

    String text

       static belongsTo = [planet: Planet, trade: Trade]

    static constraints = {
        text blank: false
        planet nullable: true, validator: {field, inst -> (inst.trade || field) && (inst.trade == null || field == null)}
        trade nullable: true
    }
}
