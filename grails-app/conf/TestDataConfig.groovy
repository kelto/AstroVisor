testDataConfig {
    sampleData {
        'com.astrovisor.StellarSystem' {
            def i = 1
            code_name = {-> "code_name${i++}" }
        }
    }
}
