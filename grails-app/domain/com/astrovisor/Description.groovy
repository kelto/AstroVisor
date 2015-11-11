package com.astrovisor

class Description {

    String text
    int upvotes
    int downvotes

    static belongsTo = [planet: Planet, trade: Trade, user: User]

    static mapping = {
        text type: "text"//Overcomes VARCHAR(255) limit
    }

    static constraints = {
        text blank: false
        planet nullable: true, validator: {field, inst -> (inst.trade || field) && (inst.trade == null || field == null)}
        trade nullable: true
        user nullable: true
    }
}
