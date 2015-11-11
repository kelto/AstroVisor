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
        def system = StellarSystem.get(1)

        String desc =
        "<p>Seule une poignée de stations héliothermiques ont pu être aménagées sur les \"pics de lumière éternelle\" situés aux pôles nord et sud de Mercure. En effet, la proximité de cette planète avec le soleil et sa vitesse de révolution orbitale élevée limitent considérablement son développement.</p>"

        def climate = new Climate(minTemp:-183, maxTemp:427, meanTemp:169, seasons:1, type:DRY)
        def orbit = new Orbit(semimajor_axis:10000, semiminor_axis:10000, orbital_speed:50.0, revolution_period:365);
        def planet = new Planet(age:4000000000, name: 'Mercure',
        texture: "mercury", description:desc, system:system, orbit:orbit,
        type: TELLURIC, size: SMALL, atmosphere: false, climate:climate)
        assert planet.save(failOnError:true, flush:true, insert: true)

        desc = "<p>Vénus est la deuxième planète du Système Solaire.</p>"+
        "<p>En raison de ses températures extrêmes, de ses nuages d'acide sulfurique et de son atmosphère saturée en dioxyde de carbone, Vénus ne compte qu'une poignée de stations de recherche.</p>"
        orbit = new Orbit(semimajor_axis:10000, semiminor_axis:10000, orbital_speed:50.0, revolution_period:365);
        planet = new Planet(age:4000000000, name: 'Vénus',
                texture: "venus", description:desc, system:system, orbit:orbit,
                type: TELLURIC, size: NORMAL, atmosphere: false)
        assert planet.save(failOnError:true, flush:true, insert: true)

        desc =
        "<p>Le berceau de l'humanité entame un nouvel âge d'or. Les richesses générées par les matières premières d'une douzaines de colonies et d'une centaine d'avant-postes industriels permettent au commerce, à l'industrie et à l'art de prospérer. Les grandes cités verdissent grâce aux arcologies et à la généralisation du télétravail, qui permettent une meilleure utilisation de la surface du sol.</p>" +
        "<p>La Terre est toujours divisée en États-nations, mais tous sont unis sous la bannière de l'Alliance interstellaire. Si les être humains jouissent d'une vie plus longue et plus heureuse, les inégalités ne cessent de s'accroître. Les nations les plus développées ont éliminé la majorité des maladies génétiques ainsi que les problèmes de pollution. Les régions les moins favorisées, en revanche, n'ont pas évolué technologiquement depuis le XXe siècle et leurs taudis surpeuplés suffoquent sous la pollution atmosphérique.</p>" +
        "<p>Le niveau des mers s'est élevé de deux mètres au cours des deux siècles derniers et les abus environnementaux du XXIe siècle ont provoqué un dérèglement climatique sensible. Cependant, les dernières décennies ont aussi vu des améliorations significatives dans ce domaine grâce aux avancées technologiques récentes.</p>"+
        "<blockquote>La Terre est le berceau de l'Humanité, mais on ne passe pas sa vie entière dans un berceau.<footer>Constantin Tsiolkovski</footer></blockquote>"

        climate = new Climate(minTemp:-93.2, maxTemp:56.7, meanTemp:15, seasons:4, type:MODERATE)
        orbit = new Orbit(semimajor_axis:10000, semiminor_axis:10000, orbital_speed:50.0, revolution_period:365);
        planet = new Planet(age:4000000000, name: 'La Terre',
            texture: "earth", description:desc, system:system, orbit:orbit,
            type: TELLURIC, size: NORMAL, atmosphere: true, climate:climate)
        assert planet.save(failOnError:true, flush:true, insert: true)

        desc = "<p>Autrefois candidate potentielle à l'acclimatation humaine, Mars est devenue un trou perdu suite à la découverte du vol supraluminique. Son pôle sud est une réserve historique protégée, constituée autour des ruines prothéennes qui y ont été découvertes. L'immigration et le développement sont fortement jugulés pour donner priorité à la recherche d'artefacts prothéens.</p>"
        orbit = new Orbit(semimajor_axis:10000, semiminor_axis:10000, orbital_speed:50.0, revolution_period:365);
        planet = new Planet(age:4000000000, name: 'Mars',
                texture: "mars", description:desc, system:system, orbit:orbit,
                type: TELLURIC, size: MEDIUM, atmosphere: false)
        assert planet.save(failOnError:true, flush:true, insert: true)

        desc = "<p>Les radiations mortelles de Jupiter ainsi que la pesanteur considérable qui y règne constituent un véritable frein à l'exploitation de ses satellites. L'avant-poste le plus important de son orbite est le complexe Nautilus, construit par Twin Helix sous la banquise du satellite Europe.</p>"
        orbit = new Orbit(semimajor_axis:10000, semiminor_axis:10000, orbital_speed:50.0, revolution_period:365);
        planet = new Planet(age:4000000000, name: 'Jupiter',
                texture: "jupiter", description:desc, system:system, orbit:orbit,
                type: TELLURIC, size: XXXL, atmosphere: false)
        assert planet.save(failOnError:true, flush:true, insert: true)

        desc = "<p>Depuis les années 2150, Saturne constitue l'une des principales réserves de combustible hélium-3 pour centrales à fusion. Son satellite Titan fournit des hydrocarbures et fait également office de terrain d'entrainement en environnement hostile pour les marines de l'Alliance.</p>"
        orbit = new Orbit(semimajor_axis:10000, semiminor_axis:10000, orbital_speed:50.0, revolution_period:365);
        planet = new Planet(age:4000000000, name: 'Saturne',
                texture: "saturn", description:desc, system:system, orbit:orbit,
                type: TELLURIC, size: XXL, atmosphere: false, rings:true)
        assert planet.save(failOnError:true, flush:true, insert: true)

        desc = "<p>Peu après la découverte de la technologie SLM, la lointaine Uranus fut particulièrement convoitée pour ses ressources abondantes d'hélium-3 et ses réserves hydriques peu profondes pour une géante gazeuse. Aujourd'hui, Uranus fournit la majeure partie du carburant He-3 de l'Alliance.</p>"
        orbit = new Orbit(semimajor_axis:10000, semiminor_axis:10000, orbital_speed:50.0, revolution_period:365);
        planet = new Planet(age:4000000000, name: 'Uranus',
                texture: "uranus", description:desc, system:system, orbit:orbit,
                type: TELLURIC, size: LARGE, atmosphere: false, rings:true)
        assert planet.save(failOnError:true, flush:true, insert: true)

        desc = "<p>Neptune est gorgée d'hélium à l'instar d'Uranus, mais son éloignement dans le système solaire en faisait un site difficilement exploitable jusqu'au développement des technologies SLM. Aujourd'hui encore, on lui préfère Uranus, plus facile à exploiter et donc plus rentable. La seule présence humaine permanente en orbite se trouve dans un complexe de recherche établi sur Triton.</p>"
        orbit = new Orbit(semimajor_axis:10000, semiminor_axis:10000, orbital_speed:50.0, revolution_period:365);
        planet = new Planet(age:4000000000, name: 'Neptune',
                texture: "neptune", description:desc, system:system, orbit:orbit,
                type: TELLURIC, size: LARGE, atmosphere: false)
        assert planet.save(failOnError:true, flush:true, insert: true)

        desc = "<p>Pluton est l'une des nombreuses \"naines de glace\" du système solaire humain. Cette planète naine est surtout connue dans la galaxie comme \"ancre\" gravitationnelle du relais cosmodésique conduisant au système Arcturus. Pluton et le relais de Charon, autrefois pris dans la glace et considéré comme un satellite, gravitent en rotation mutuelle synchrone. Suite à la récupération du relais cosmodésique de Charon, l'orbite de Pluton a été circularisée en 2157.</p>"
        orbit = new Orbit(semimajor_axis:10000, semiminor_axis:10000, orbital_speed:50.0, revolution_period:365);
        planet = new Planet(age:4000000000, name: 'Pluton',
                texture: "pluto", description:desc, system:system, orbit:orbit,
                type: TELLURIC, size: SMALL, atmosphere: false)
        assert planet.save(failOnError:true, flush:true, insert: true)

        system = StellarSystem.get(2)
        desc = "<p>Watson a fait la une des médias humain à deux titres : ses marée spectaculaires provoquées par une lne disproportionnée, et l'imbroglio administratif quant à la primeur de sa découverte. Watson est en effet un monde-éden revendiqué simultanément en 2165 par la Fédération populaire de Chine, les Etats-Unis d'Amérique du Nord et l'Union européenne. L'Alliance interstellaire a donc négocié le tristement célèbre \"compromis de Reykjavik\" qui accorde à chaque coalition un droit de colonisation limité, dans des villes constituées de ressortissants de chaque nation.</p>"+
        "<p>Watson elle-même est plus froide que la Terre . Sa zone tempérée s'étend sur environ 30 degrés de latitude de part et d'autre de l'équateur. L'évolution du biotope local diffère sensiblement de celle de la Terre : certaines îles comportent des espèces semblables aux mammifères terrestres à placenta, d'autres grouillent d'arthropodes. On estime qu'il faudra encore au moins deux générations de xénozoologistes pour dresser une classification à peu près exhaustive des espèces locales.</p>"
        orbit = new Orbit(semimajor_axis:10000, semiminor_axis:10000, orbital_speed:50.0, revolution_period:365);
        planet = new Planet(code_name:'LVMA-450b', age:4000000000, name: 'Watson',
                texture: "watson", description:desc, system:system, orbit:orbit,
                type: TELLURIC, size: NORMAL, atmosphere: true)
        assert planet.save(failOnError:true, flush:true, insert: true)

        system = StellarSystem.get(3)
        desc = "<p>Eden Prime est une planète-éden où la vie et la biodiversité de type terrien est favorisée. Cette planète fut colonisée et adaptée pour devenir un monde-grenier pour et par les humains.</p>"
        orbit = new Orbit(semimajor_axis:10000, semiminor_axis:10000, orbital_speed:50.0, revolution_period:365);
        planet = new Planet(code_name:'ZetaReticuli-03d', age:4000000000, name: 'Eden Prime',
                texture: "eden_prime", description:desc, system:system, orbit:orbit,
                type: TELLURIC, size: NORMAL, atmosphere: true)
        assert planet.save(failOnError:true, flush:true, insert: true)
    }

    def descriptionTestData(){
        def planet = Planet.get(1)
        def user = User.get(1)
        def description = new Description(text:"Planète parfaite pour un bronzage intégrale, je recommande chaudement !", planet: planet, user: user)
        assert description.save(failOnError:true, flush:true, insert: true)
        description = new Description(text:"Très belle vue sur le Soleil, 10/10 IGN", planet: planet, user: user)
        assert description.save(failOnError:true, flush:true, insert: true)

        planet = Planet.get(3)
        description = new Description(text:"Les habitants de cette planète sont les plus malpolis de la galaxie !", planet: planet, user: user)
        description.save()

        description = new Description(text:"Mégalopoles surpeuplées et polluées, à éviter donc !", planet: planet, user: user)
        description.save()

        assert Description.count == 4
    }

    def tradeTestData(){
        def planet = Planet.get(1)
        def trade = new Trade(name: "tradeBoot", planet: planet)
        trade.save(flush: true, failOnError: true)
        trade = new Trade(name: "TradeBootaaaa", planet: planet);
        trade.save(flush: true, failOnError: true)
    }
}
