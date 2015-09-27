import astrovisor.Planet

class BootStrap {

    def init = { servletContext ->
        environments {
            production {
                "cd yo && gulp".execute();
            }

            development {
                "cd yo && gulp serve".execute();
            }
        }
        def result = '################## running in UNCLEAR mode.'
        println "Application starting ... "
        seedTestData()

        println "$result"
    }

    private void seedTestData() {
        def planet = null
        println "Start loading cities into database"
        planet = new Planet(name: 'kelto')
        assert planet.save(failOnError:true, flush:true, insert: true)
        planet.errors = null

        planet = new Planet(name: 'keltorin')
        assert planet.save(failOnError:true, flush:true, insert: true)
        planet.errors = null

        assert Planet.count == 2;
        println "Finished loading $Planet.count cities into database"
    }
        def destroy = {
    }
}
