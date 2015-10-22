import com.astrovisor.Description
import com.astrovisor.Planet
import com.astrovisor.User
import com.astrovisor.Trade

import grails.util.Environment

import static com.astrovisor.Planet.Type.*

class BootStrap {

    def init = { servletContext ->
        if (Environment.current == Environment.DEVELOPMENT) {
            userTestData()
            planetTestData()
            tradeTestData()
        }
    }

    private void planetTestData() {
        println "Start loading planets into database"

        def planet = new Planet(code_name: "XO-000",age:0, name: 'kelto',
                                image: "image", description: "description",
                                type: GAS, region: "region")
        assert planet.save(failOnError:true, flush:true, insert: true)
        def description = new Description(text: "Hehehe", planet: planet)
        assert description.save(failOnError:true, flush:true, insert: true)
        description = new Description(text: "Second desc", planet: planet);
        assert description.save(failOnError:true, flush:true, insert: true)

        planet = new Planet(code_name: "XO-001",age:0, name: 'keltorin',
                            image: "image", description: "desc",
                            type: TELLURIC, region: "region")
        assert planet.save(failOnError:true, flush:true, insert: true)

        assert Planet.count == 2;
        println "Finished loading $Planet.count planets into database"
    }
        def destroy = {
    }

    def userTestData() {
        User user = new User(username:"user", password: "password")
        user.save()
    }

    def tradeTestData(){
        def planet = Planet.get(1)
        def trade = new Trade(name: "tradeBoot", planet: planet)
        trade.save(flush: true, failOnError: true)
    }
}
