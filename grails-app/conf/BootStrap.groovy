import com.astrovisor.Description
import com.astrovisor.Planet
import com.astrovisor.User
import com.astrovisor.Trade
import com.astrovisor.StellarSystem
import com.astrovisor.Orbit

import grails.util.Environment
import grails.converters.JSON

import static com.astrovisor.Planet.Type.*

class BootStrap {

    def init = { servletContext ->
        if (Environment.current == Environment.DEVELOPMENT) {
            userTestData()
            stellarSystemTestData()
            planetTestData()
            descriptionTestData()
            tradeTestData()
        }
        JSON.registerObjectMarshaller(StellarSystem) {
            return [code_name: it.code_name, name:it.name,
                    planets:it.planets]
        }
    }

    def destroy = {

    }

    def userTestData(){
        User user = new User(username:"John Doe", password: "123456A")
        assert user.save(failOnError: true, flush: true, insert: true)

        user = new User(username:"Jane Doe", password: "123456A")
        user.save(failOnError: true, flush: true, insert: true)

        assert User.count == 2
    }

    def stellarSystemTestData(){
        def system = new StellarSystem(code_name:"S0-000", name:"Système Local")
        assert system.save(failOnError: true, flush: true, insert: true)

        system = new StellarSystem(code_name:"S0-001", name:"Alpha Centauri")
        system.save(failOnError: true, flush: true, insert: true)

        system = new StellarSystem(code_name:"S0-002", name:"Proxima Centauri")
        system.save(failOnError: true, flush: true, insert: true)

        assert StellarSystem.count == 3
    }

    def planetTestData() {
        def orbit = new Orbit(semimajor_axis:10000, semiminor_axis:10000,
                              orbital_speed:50.0, revolution_period:365)
        def system = StellarSystem.get(1)

        def planet = new Planet(code_name: "XO-000", age:0, name: 'kelto',
            image: "mars", description: "desc", system:system,
            type: GAS, orbit: orbit)
        assert planet.save(failOnError:true, flush:true, insert: true)

        planet = new Planet(code_name: "XO-001",age:0, name: 'keltorin',
            image: "pluto", description: "desc", system:system,
            type: TELLURIC, orbit: orbit)
        assert planet.save(failOnError:true, flush:true, insert: true)
        assert Planet.count == 2;
    }

    def descriptionTestData(){
        def planet = Planet.get(1)
        def description = new Description(text:"Hehehe", planet: planet)
        assert description.save(failOnError:true, flush:true, insert: true)

        planet = Planet.get(2)
        description = new Description(text:"Hehehe", planet: planet)
        assert description.save(failOnError:true, flush:true, insert: true)

        assert Description.count == 2
    }

    def tradeTestData(){
        def planet = Planet.get(1)
        def trade = new Trade(name: "tradeBoot", planet: planet)
        trade.save(flush: true, failOnError: true)
        trade = new Trade(name: "TradeBootaaaa", planet: planet);
        trade.save(flush: true, failOnError: true)
    }
}
