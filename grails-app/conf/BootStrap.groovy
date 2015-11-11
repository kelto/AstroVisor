import com.astrovisor.Climate
import com.astrovisor.Description
import com.astrovisor.Planet
import com.astrovisor.Orbit
import com.astrovisor.User
import com.astrovisor.Trade
import com.astrovisor.StellarSystem

import grails.util.Environment
import grails.converters.JSON

import static com.astrovisor.Planet.Type.*
import static com.astrovisor.Planet.Size.*

import static com.astrovisor.Climate.ClimateType.*

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
                    planets:it.planets
            ]
        }

        JSON.registerObjectMarshaller(Planet) {
            return [id:it.id, code_name: it.code_name, name:it.name, age:it.age, texture:it.texture,
                    descriptions:it.descriptions, trades:it.trades, type:it.type, size:it.size, climate:it.climate,
                    description:it.description, rings:it.rings, atmosphere:it.atmosphere, orbit:it.orbit
            ]
        }

        JSON.registerObjectMarshaller(User) {
            return [username: it.username, totalVotes: it.totalVotes
            ]
        }
    }

    def destroy = {

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
        description = new Description(text: "Desc of second planet", planet: planet);
        assert description.save(failOnError:true, flush:true, insert: true)
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
        assert trade.save(failOnError:true, flush: true, insert:true)

        assert Trade.count == 1
    }
}
