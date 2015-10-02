package com.astrovisor

class Orbit {

    float semimajor_axis
    float semiminor_axis

    float orbital_speed
    float revolution_period

    static constraints = {
        semimajor_axis validator: { val ->
            return val > 0.0
        }
        semiminor_axis validator: { val ->
            return val > 0.0
        }
        orbital_speed validator: { val ->
            return val > 0.0
        }
        revolution_period validator: { val ->
            return val > 0.0
        }
    }
}