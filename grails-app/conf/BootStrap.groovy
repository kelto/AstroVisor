import com.astrovisor.Description
import com.astrovisor.Planet
import com.astrovisor.User
import com.astrovisor.Trade

import grails.util.Environment

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
        def planet = new Planet(code_name: "XO-000",age:0, name: 'kelto')
        assert planet.save(failOnError:true, flush:true, insert: true)
        planet.errors = null

        planet = new Planet(code_name: "XO-001",age:0, name: 'keltorin')
        assert planet.save(failOnError:true, flush:true, insert: true)
        planet.errors = null

        assert Planet.count == 2;
        println "Finished loading $Planet.count planets into database"

        def description = new Description(text: "Hehehe", planet: planet)
        assert description.save(failOnError:true, flush:true, insert: true)
    }
        def destroy = {
    }

    def userTestData() {
        User user = new User(username:"user", password: "password")
        user.save()
    }

    def tradeTestData(){
        def trade = new Trade(name: "tradeBoot")
        trade.save()
    }
}
