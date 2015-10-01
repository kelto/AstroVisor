import astrovisor.Planet

class BootStrap {

    def init = { servletContext ->
        seedTestData()
    }

    private void seedTestData() {
        def planet = null
        println "Start loading planets into database"
        planet = new Planet(code_name: "XO-000",age:0, name: 'kelto')
        assert planet.save(failOnError:true, flush:true, insert: true)
        planet.errors = null

        planet = new Planet(code_name: "XO-001",age:0, name: 'keltorin')
        assert planet.save(failOnError:true, flush:true, insert: true)
        planet.errors = null

        assert Planet.count == 2;
        println "Finished loading $Planet.count planets into database"
    }
        def destroy = {
    }
}
