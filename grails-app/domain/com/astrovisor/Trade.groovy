package com.astrovisor

import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true, excludes = "metaClass,class")
class Trade {
    
    String name


    static constraints = {
        name blank: false 
    }
}
