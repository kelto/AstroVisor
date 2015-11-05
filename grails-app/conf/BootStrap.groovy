import com.astrovisor.Description
import com.astrovisor.Planet
import com.astrovisor.Orbit
import com.astrovisor.User
import com.astrovisor.Trade
import com.astrovisor.StellarSystem

import grails.util.Environment

import static com.astrovisor.Planet.Type.*
import static com.astrovisor.Planet.Size.*

class BootStrap {

    def init = { servletContext ->
        if (Environment.current == Environment.DEVELOPMENT) {
            userTestData()
            stellarSystemTestData()
            planetTestData()
            descriptionTestData()
            tradeTestData()
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
        def system = StellarSystem.get(2)
        def orbit = new Orbit(semimajor_axis:10000, semiminor_axis:10000, orbital_speed:20.0, revolution_period:450);

        def planet = new Planet(code_name: "XO-000", age:0, name: 'kelto',
            texture: "mars", description: "desc", system:system, orbit:orbit,
            type: GAS, size: SMALL)
        assert planet.save(failOnError:true, flush:true, insert: true)

        orbit = new Orbit(semimajor_axis:10000, semiminor_axis:10000, orbital_speed:250.0, revolution_period:450);
        planet = new Planet(code_name: "XO-001", age:0, name: 'keltorin',
            texture: "pluto", description: "desc", system:system, orbit:orbit,
            type: TELLURIC, size: SMALL)
        assert planet.save(failOnError:true, flush:true, insert: true)

        system = StellarSystem.get(1)
        orbit = new Orbit(semimajor_axis:10000, semiminor_axis:10000, orbital_speed:50.0, revolution_period:365);
        planet = new Planet(code_name: "XO-002", age:0, name: 'La Terre',
                texture: "earth", description: "desc", system:system, orbit:orbit,
                type: TELLURIC, size: SMALL, atmosphere: true)
        assert planet.save(failOnError:true, flush:true, insert: true)

        assert Planet.count == 3;
    }

    def descriptionTestData(){
        def planet = Planet.get(1)
        def description = new Description(text:"Hehehe", planet: planet)
        assert description.save(failOnError:true, flush:true, insert: true)

        planet = Planet.get(2)
        description = new Description(text:"Hehehe", planet: planet)
        description.save()

        assert Description.count == 2
    }

    def tradeTestData(){
        def planet = Planet.get(1)
        def trade = new Trade(name: "tradeBoot", planet: planet)
        assert trade.save(failOnError:true, flush: true, insert:true)

        assert Trade.count == 1
    }
}
