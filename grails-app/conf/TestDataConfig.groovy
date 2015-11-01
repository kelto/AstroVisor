testDataConfig {
    sampleData {
        'com.astrovisor.StellarSystem' {
            def i = 1
            code_name = {-> "code_name${i++}" }
        }
        'com.astrovisor.Orbit' {
            semimajor_axis = 10000
            semiminor_axis = 10000
            orbital_speed = 50.0
            revolution_period = 365
        }
    }
}
